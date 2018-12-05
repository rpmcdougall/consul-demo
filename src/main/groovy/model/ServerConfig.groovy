package model

class ServerConfig {
    String fqdn
    String ip
    String role
    List<Integer> ports

    @Override
    public String toString() {
        return "ServerConfig{" +
                "fqdn='" + fqdn + '\'' +
                ", ip='" + ip + '\'' +
                ", role='" + role + '\'' +
                ", ports=" + ports +
                '}';
    }
}
