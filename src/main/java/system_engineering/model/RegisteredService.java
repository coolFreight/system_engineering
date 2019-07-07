package system_engineering.model;

import java.time.LocalDate;

public class RegisteredService {
    private String serviceName;
    private String ip;
    private LocalDate uptime;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public LocalDate getUptime() {
        return uptime;
    }

    public void setUptime(LocalDate uptime) {
        this.uptime = uptime;
    }

    @Override
    public String toString() {
        return "RegisteredService{" +
                "serviceName='" + serviceName + '\'' +
                ", ip='" + ip + '\'' +
                ", uptime=" + uptime +
                '}';
    }
}
