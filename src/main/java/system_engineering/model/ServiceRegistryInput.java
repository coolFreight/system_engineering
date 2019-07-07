package system_engineering.model;

public class ServiceRegistryInput {

    private String service;
    private String ip;

    public ServiceRegistryInput(String service, String ip) {
        this.service = service;
        this.ip = ip;
    }

    public String getService() {
        return service;
    }

    public String getIp() {
        return ip;
    }
}