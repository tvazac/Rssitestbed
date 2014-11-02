#include "rssitestbedconfig.h"
#include "rssitestbedmsgs.h"

module RssiTestbedC {
	uses interface Boot;
	uses interface Timer<TMilli> as SendTimer;
	uses interface Leds;

	uses interface AMSend as RssiMsgSend;
	uses interface Receive as RssiMsgReceiver;
	uses interface SplitControl as RadioControl;
	uses interface AMPacket as RssiAMPacket;

	uses interface SplitControl as SerialControl;
	uses interface Packet as UartAMPacket;

	uses interface AMSend as UartSend;
	uses interface Receive as UartReceive;

	uses interface AMSend as RssiNoiseReportSend;
	uses interface Timer<TMilli> as RssiNoiseFloorTimer;
	uses interface Packet as RssiNoiseReportPacket;

#ifdef __CC2420_H__
  uses interface CC2420Packet;
#elif defined(TDA5250_MESSAGE_H)
  uses interface Tda5250Packet;    
#else
  uses interface PacketField<uint8_t> as PacketRSSI;
#endif

#ifdef __CC2420_H__
  // noise floor rssi reading
  uses interface Read<uint16_t> as ReadRssi;
#endif

#define RSSI_OFFSET 45

} implementation {
	rssi_report_msg_t rssiToReport;
	message_t packet;
	uint8_t packetLen;
	message_t * ONE_NOK uartPacket;
	int16_t rssi;
	uint8_t power = 1;

	bool radio = FALSE;

	uint16_t noiseFloor;

	uint16_t packetsToSend = 10;

	int16_t getRssi(message_t *msg);
	void setPower(message_t *, uint8_t);
	error_t readNoiseFloor();

	event void Boot.booted() {
		call RadioControl.start();
		call SerialControl.start();
	}

	event void SerialControl.startDone(error_t error) {
		call RssiNoiseFloorTimer.startPeriodic(NOISE_FLOOR_READING_INTERVAL_MS);
  	}

	event void SerialControl.stopDone(error_t error) {
  	}

	event void UartSend.sendDone(message_t* msg, error_t error) {
	}

	event void RssiNoiseReportSend.sendDone(message_t* msg, error_t error) {
	}

	task void uartSendTask() {
		rssi_report_msg_t *rssiReportMsg = (rssi_report_msg_t *)call UartAMPacket.getPayload(&packet, sizeof(rssi_report_msg_t));	
		
		rssiReportMsg->nodeId = TOS_NODE_ID;
		rssiReportMsg->rssi = rssiToReport.rssi - RSSI_OFFSET;
		rssiReportMsg->sourceId = rssiToReport.sourceId;

		if(call UartSend.send(TOS_NODE_ID, &packet, sizeof(rssi_report_msg_t)) == SUCCESS) {
			call Leds.led1Toggle();
		} else {
			call Leds.led2Toggle();
		}
	}

	event message_t * UartReceive.receive(message_t *cmdmsg, void *payload, uint8_t len) {
		if(sizeof(command_msg_t) == len) {
			command_msg_t* cmd = (command_msg_t*)payload;
			packetsToSend = cmd->packets;
			power = cmd->power;
			call Leds.set(7);
			if(packetsToSend > 0) {
				//call SendTimer.startPeriodic(PACKET_SENDING_INTERVAL_MS);
				call Leds.led2Toggle();
				setPower(&packet, power);
				radio = TRUE;
				call RssiMsgSend.send(AM_BROADCAST_ADDR, &packet, sizeof(RssiMsg));		
			}
		}

		return cmdmsg;
	}

	event void RadioControl.startDone(error_t result) {
	}

	event void RadioControl.stopDone(error_t result) {

	}

	event void RssiNoiseFloorTimer.fired() {
		readNoiseFloor();
	}

	event void SendTimer.fired() {
		call Leds.led2Toggle();
		setPower(&packet, power);
		
		call RssiMsgSend.send(AM_BROADCAST_ADDR, &packet, sizeof(RssiMsg));		
	}

	event void RssiMsgSend.sendDone(message_t *m, error_t error) {
		call Leds.led2Toggle();
		packetsToSend--;
		if(packetsToSend == 0) {
			call SendTimer.stop();
			call Leds.set(0);
		}
		radio = FALSE;
	}

	event message_t * RssiMsgReceiver.receive(message_t *rssimsg, void *payload, uint8_t len) {
		rssiToReport.rssi = getRssi(rssimsg);
		rssiToReport.sourceId = call RssiAMPacket.source(rssimsg);
		post uartSendTask();
		return rssimsg;
	}

	task void readNoiseTask() {
		readNoiseFloor();
	}

	task void sendNoiseReading() {
		rssi_noise_report_msg_t *rssiNoiseReportMsg = (rssi_noise_report_msg_t *)call RssiNoiseReportPacket.getPayload(&packet, sizeof(rssi_noise_report_msg_t));	
		
		rssiNoiseReportMsg->nodeId = TOS_NODE_ID;
		rssiNoiseReportMsg->noise = noiseFloor;

		if(call RssiNoiseReportSend.send(TOS_NODE_ID, &packet, sizeof(rssi_noise_report_msg_t)) == SUCCESS) {
			call Leds.led1Toggle();
		} else {
			call Leds.led2Toggle();
		}
	}

#ifdef __CC2420_H__  
	int16_t getRssi(message_t *msg){
    	return (int16_t) call CC2420Packet.getRssi(msg);
  	}
#elif defined(CC1K_RADIO_MSG_H)
  	int16_t getRssi(message_t *msg){
    	cc1000_metadata_t *md =(cc1000_metadata_t*) msg->metadata;
    	return md->strength_or_preamble;
  	}
#elif defined(PLATFORM_IRIS) || defined(PLATFORM_UCMINI)
  	int16_t getRssi(message_t *msg){
    	if(call PacketRSSI.isSet(msg))
      		return (int16_t) call PacketRSSI.get(msg);
    	else
      		return 0xFFFF;
	}
#elif defined(TDA5250_MESSAGE_H)
   	int16_t getRssi(message_t *msg){
    	return call Tda5250Packet.getSnr(msg);
   	}
#else
  #error Radio chip not supported! This demo currently works only \
         for motes with CC1000, CC2420, RF230, RFA1 or TDA5250 radios.  
#endif

#ifdef __CC2420_H__
	void setPower(message_t *msg, uint8_t power){
		if (power >= 1 && power <=31){
			call CC2420Packet.setPower(msg, power);
		}
	}

	error_t readNoiseFloor(){
		if (radio == TRUE) {
			return FAIL;
		}

		return call ReadRssi.read();
	}

	// noise floor results
	event void ReadRssi.readDone(error_t error, uint16_t data) {
		if (error==SUCCESS){
			
			noiseFloor = data;
			post sendNoiseReading();

		} else {
			// noise floor reading failed, start again
			post readNoiseTask();
		}
	}
#endif


}