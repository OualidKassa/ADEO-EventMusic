package cdp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;




@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getEvents() {
        return eventRepository.findAllBy();
    }

    public void delete(Long id) {
        eventRepository.deleteById(id);
    }

    public List<Event> getFilteredEvents(String query) {
        List<Event> events = eventRepository.findAllBy();
        // Filter the events list in pure JAVA here

        return filterEventsByQuery(events, query);
    }

    public  List<Event> filterEventsByQuery(List<Event> events, String query) {
        List<Event> eventsFilter = new ArrayList<>(events);

        return eventsFilter.stream()
                .peek(event -> event.setBands(filterBandsByEventAndQuery(event, query)))
                .peek(count -> count.setTitle(count.getTitle() +" ["+count.getBands().size()+"]"))
                .filter(event -> !event.getBands().isEmpty())
                .collect(Collectors.toList());
    }

    private  Set<Band> filterBandsByEventAndQuery(Event pEvent, String query) {
        return pEvent.getBands().stream()
                .peek(band -> band.setMembers(filterMembersByBandAndQuery(band, query)))
                .peek(count -> count.setName(count.getName() + " ["+count.getMembers().size()+"]"))
                .filter(band -> !band.getMembers().isEmpty())
                .collect(Collectors.toSet());
    }

    private  Set<Member> filterMembersByBandAndQuery(Band pBand, String query) {
        return pBand.getMembers().stream()
                .filter(member -> member.getName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toSet());
    }


    public void update(Long id, Event event) {
        eventRepository.updateNbStarsAndComment(id, event.getNbStars(), event.getComment());
    }
}
