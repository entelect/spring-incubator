package entelect.training.incubator.spring.customer.model;

public enum SearchType {
    NAME_SEARCH("findByFirstNameAndLastName"),
    PASSPORT_SEARCH("findByPassportNumber"),
    USER_SEARCH("findByUsername");

    private final String searchType;

    SearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getSearchType() {
        return searchType;
    }

    public static SearchType fromString(String text) {
        for (SearchType b : SearchType.values()) {
            if (b.searchType.equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new IllegalArgumentException("No SearchType of type " + text + " found");
    }
}