package wawer.kamil.beerproject.configuration.envers;


import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomRevisionListener implements RevisionListener {
    @Override
    public void newRevision(Object revisionEntityObject) {
        Revision revisionEntity = (Revision) revisionEntityObject;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        revisionEntity.setChangedBy(username);
    }
}
