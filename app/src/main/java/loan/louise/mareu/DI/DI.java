package loan.louise.mareu.DI;

import loan.louise.mareu.service.ApiService;
import loan.louise.mareu.service.MeetingApiService;

public class DI {

    private static ApiService service = new MeetingApiService();

    public static ApiService getMeetingApiService() {
        return service;
    }
}
