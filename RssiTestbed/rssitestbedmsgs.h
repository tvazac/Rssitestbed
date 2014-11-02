#ifndef _RSSITESTBEDMSGS_H_
#define _RSSITESTBEDMSGS_H_

enum {
	AM_RSSI_MSG = 1,
	AM_RSSIREPORTMSG = 2,
	AM_COMMANDMSG = 3,
	AM_RSSINOISEREPORTMSG = 4
};

typedef nx_struct RssiMsg {
	nx_int16_t rssi;
} RssiMsg;

typedef nx_struct RssiReportMsg {
	nx_uint8_t nodeId;
	nx_int16_t rssi;
	nx_uint8_t sourceId;
} rssi_report_msg_t;

typedef nx_struct CommandMsg {
	nx_uint8_t power;
	nx_uint16_t packets;
} command_msg_t;

typedef nx_struct RssiNoiseReportMsg {
	nx_uint8_t nodeId;
	nx_uint16_t noise;
} rssi_noise_report_msg_t;

#endif //_RSSITESTBEDMSGS_H_