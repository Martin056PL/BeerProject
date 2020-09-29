package wawer.kamil.beerproject.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import wawer.kamil.beerproject.dto.request.PageParams;

public class PageableCreator {

    public static PageRequest createPageable(PageParams pageParams) {
        return PageRequest.of(
                determinePageNumber(pageParams),
                determinePageSize(pageParams),
                determineSorting(pageParams));
    }

    private static int determinePageNumber(PageParams pageParams) {
        Integer page = pageParams.getPageNumber();
        return validatePageNumber(page) ? page : 0;
    }

    private static boolean validatePageNumber(Integer pageNumber) {
        return pageNumber != null && pageNumber >= 0;
    }

    private static int determinePageSize(PageParams pageParams) {
        Integer size = pageParams.getPageSize();
        return validatePageSize(size) ? size : 20;
    }

    private static boolean validatePageSize(Integer pageSize) {
        return pageSize != null && pageSize >= 0;
    }

    private static Sort determineSorting(PageParams pageParams) {
        Sort.Direction directionOfSort = determineDirectionOfSort(pageParams);
        String sortBy = determineSortingBy(pageParams);
        return Sort.by(directionOfSort, sortBy);
    }

    private static Sort.Direction determineDirectionOfSort(PageParams pageParams) {
        Sort.Direction order = pageParams.getSortDirection();
        return validateDirectionParam(order) ? order : Sort.Direction.ASC;
    }

    private static boolean validateDirectionParam(Sort.Direction order) {
        return order != null && order.name().equals("DESC");
    }

    private static String determineSortingBy(PageParams pageParams) {
        String sortBy = pageParams.getSortBy();
        return validateSortByParam(sortBy) ? sortBy : "breweryId";
    }

    private static boolean validateSortByParam(String sortBy) {
        return sortBy != null && !sortBy.equals("breweryId");
    }
}
