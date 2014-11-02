#include "rssitestbedmsgs.h"

configuration RssiTestbedAppC {

} implementation {
	components ActiveMessageC, MainC, LedsC;
	components new AMSenderC(AM_RSSI_MSG) as RssiMsgSenderC;
	components new AMReceiverC(AM_RSSI_MSG) as RssiMsgReceiverC;
	components new TimerMilliC() as SendTimer;
	components new TimerMilliC() as RssiNoiseFloorTimer;

	components SerialActiveMessageC;
	components new SerialAMSenderC(AM_RSSIREPORTMSG);
	components new SerialAMReceiverC(AM_COMMANDMSG);
	components new SerialAMSenderC(AM_RSSINOISEREPORTMSG) as RssiNoiseReportSender;

	components RssiTestbedC as App;

	App.Boot -> MainC;
	App.SendTimer -> SendTimer;
	App.Leds -> LedsC;

	App.RssiMsgSend -> RssiMsgSenderC;
	App.RssiMsgReceiver -> RssiMsgReceiverC;
	App.RadioControl -> ActiveMessageC;
	App.RssiAMPacket -> RssiMsgSenderC;


	App.UartSend -> SerialAMSenderC;
	App.UartAMPacket -> SerialAMSenderC;
	App.SerialControl -> SerialActiveMessageC;
	App.UartReceive -> SerialAMReceiverC;

	App.RssiNoiseReportSend -> RssiNoiseReportSender;
	App.RssiNoiseReportPacket -> RssiNoiseReportSender;
	App.RssiNoiseFloorTimer -> RssiNoiseFloorTimer;

	components CC2420ActiveMessageC;
  	App -> CC2420ActiveMessageC.CC2420Packet;

  	components CC2420ControlP;
  	App.ReadRssi -> CC2420ControlP;

#ifdef __CC2420_H__
  components CC2420ActiveMessageC;
  App -> CC2420ActiveMessageC.CC2420Packet;
  components CC2420ControlP;
  App.ReadRssi -> CC2420ControlP;
#elif  defined(PLATFORM_IRIS)
  components  RF230ActiveMessageC;
  App -> RF230ActiveMessageC.PacketRSSI;
#elif defined(PLATFORM_UCMINI)
  components  RFA1ActiveMessageC;
  App -> RFA1ActiveMessageC.PacketRSSI;
#elif defined(TDA5250_MESSAGE_H)
  components Tda5250ActiveMessageC;
  App -> Tda5250ActiveMessageC.Tda5250Packet;
#endif

}