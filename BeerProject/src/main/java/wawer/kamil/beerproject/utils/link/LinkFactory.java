package wawer.kamil.beerproject.utils.link;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.model.email.EmailType;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class LinkFactory {

    private final Map<EmailType, LinkProvider> map;

    public LinkProvider getLinkProvider(EmailType type){
        return map.get(type);
    }
}
