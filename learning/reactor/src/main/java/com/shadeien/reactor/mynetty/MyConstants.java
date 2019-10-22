package com.shadeien.reactor.mynetty;

public interface MyConstants {
    enum MessageType {
        HEARBEAT_REQ("1", 1),
        HEARBEAT_RESP("2", 2),
        LOGIN_REQ("3", 3),
        LOGIN_RESP("4", 4);

        byte value;
        String name;
        MessageType(String name, int value) {
            this.value = (byte) value;
            this.name = name;
        }

        public byte getValue() {
            return value;
        }

        public void setValue(byte value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
