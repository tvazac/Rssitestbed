COMPONENT=RssiTestbedAppC

ifneq ($(filter iris,$(MAKECMDGOALS)),) 
	CFLAGS += -DRF230_RSSI_ENERGY
endif

CFLAGS += $(INCLUDES)

include $(MAKERULES)
