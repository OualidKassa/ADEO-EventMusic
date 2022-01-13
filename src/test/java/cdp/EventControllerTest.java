package cdp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventController.class)
class EventControllerTest {


    @MockBean
     EventService eventService;

    @Autowired
     EventController eventController;


     Event event = new Event();
     List<Event> eventList = new ArrayList<>();

    @BeforeEach
    public void initialize() {
        event.setId(1L);
        event.setTitle("test");
        event.setNbStars(3);
        eventList.add(event);
    }

    @Test
    public void findEventsTest1() {
        when(eventService.getEvents()).thenReturn(eventList);
        List<Event> events = eventController.findEvents();
        verify(eventService, times(1)).getEvents();
       assertEquals("test", events.get(0).getTitle());
    }

    @Test
    public void findEventsTest2() {
        when(eventService.getFilteredEvents(anyString())).thenReturn(eventList);
        List<Event> events = eventController.findEvents("wa");
        verify(eventService, times(1)).getFilteredEvents("wa");
        assertEquals("test", events.get(0).getTitle());
    }

    @Test
    public void deleteEventTest() {
        doNothing().when(eventService).delete(any());
        eventController.deleteEvent(1L);
        verify(eventService, times(1)).delete(any());
    }

    @Test
    public void updateEventTest() {
        doNothing().when(eventService).update(any(), any());
        eventController.updateEvent(1L, new Event());
        verify(eventService, times(1)).update(any(), any());
    }
}
