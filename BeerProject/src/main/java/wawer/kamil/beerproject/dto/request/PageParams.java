package wawer.kamil.beerproject.dto.request;

import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
public class PageParams {

    private final Integer pageNumber;
    private final Integer pageSize;
    private final Sort.Direction sortDirection;
    private final String sortBy;

    private PageParams(Integer pageNumber, Integer pageSize, Sort.Direction sortDirection, String sortBy) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortDirection = sortDirection;
        this.sortBy = sortBy;
    }

    public static PageParams wrapRequestParams(Integer pageNumber, Integer pageSize, Sort.Direction sortDirection, String sortBy) {
        return new PageParams(pageNumber, pageSize, sortDirection, sortBy);
    }
}
