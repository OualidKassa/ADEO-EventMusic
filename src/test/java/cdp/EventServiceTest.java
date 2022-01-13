package cdp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventService.class)
class EventServiceTest {

    @Autowired
    EventService eventService;

    @MockBean
    EventRepository eventRepository;

    List<Event> eventsTest = new ArrayList<>();
    Set<Band> bandstest = new HashSet<>();
    Set<Band> bandstest2 = new HashSet<>();
    Set<Member> membersTestSet1 = new HashSet<>();
    Set<Member> membersTestSet2 = new HashSet<>();
    Set<Member> membersTestSet3 = new HashSet<>();

    Member membertest1 = new Member();
    Member membertest2 = new Member();
    Member membertest3 = new Member();

    Member membertest11 = new Member();
    Member membertest22 = new Member();
    Member membertest33 = new Member();

    Band bandtest1 = new Band();
    Band bandtest2= new Band();
    Band bandtest3 = new Band();

    Event eventtest1 = new Event();
    Event eventtest2 = new Event();

    @BeforeEach
    public void init(){
        membertest11.setName("david");
        membertest22.setName("Pierre");
        membertest33.setName("PAtrick");
        membersTestSet1.add(membertest11);
        membersTestSet1.add(membertest22);
        membersTestSet1.add(membertest33);

        membertest1.setName("aloah");
        membertest2.setName("philippe");
        membertest3.setName("jean");
        membersTestSet2.add(membertest1);
        membersTestSet2.add(membertest2);
        membersTestSet2.add(membertest3);

        bandtest1.setName("sum41");
        bandtest1.setMembers(membersTestSet1);

        bandtest2.setName("Rolling Stone");
        bandtest2.setMembers(membersTestSet2);

        bandstest.add(bandtest1);
        bandstest2.add(bandtest2);

        eventtest1.setTitle("concert rock");
        eventtest1.setId(1L);
        eventtest1.setBands(bandstest);

        eventtest2.setTitle("concert pop");
        eventtest2.setId(2L);
        eventtest2.setBands(bandstest2);

        eventsTest.add(eventtest1);
        eventsTest.add(eventtest2);
    }

    @Test
    void getEvents() {
       when(eventRepository.findAllBy()).thenReturn(eventsTest);
       List<Event> eventsResult = eventService.getEvents();
       verify(eventRepository, times(1)).findAllBy();
       assertEquals("concert rock", eventsResult.get(0).getTitle());
    }

    @Test
    void delete() {
        doNothing().when(eventRepository).deleteById(any());
        eventService.delete(1L);
        verify(eventRepository, times(1)).deleteById(1L);
    }


    @Test
    void filterEventsByQuery() {
        List<Event> eventsFilterTest = eventService.filterEventsByQuery(eventsTest, "aloah");
        Set<Band> bandsResult = eventsFilterTest.get(0).getBands();
        assertTrue(bandsResult.contains(bandtest2));
    }

    @Test
    void getFilteredEvents(){
        when(eventRepository.findAllBy()).thenReturn(eventsTest);
        List<Event> eventsFilterTest = eventService.getFilteredEvents( "aloah");
        Set<Band> bandsResult = eventsFilterTest.get(0).getBands();
        assertTrue(bandsResult.contains(bandtest2));
    }

    @Test
    void update() {
        doNothing().when(eventRepository).updateNbStarsAndComment(any(), any(), any());
        eventService.update(1L, new Event());
        verify(eventRepository, times(1)).updateNbStarsAndComment(any(), any(), any());
    }
}
