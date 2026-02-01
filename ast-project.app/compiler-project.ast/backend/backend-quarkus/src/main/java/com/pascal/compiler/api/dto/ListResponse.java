package com.pascal.compiler.api.dto;

import java.util.List;

public class ListResponse {
    public List<?> files;
    public int totalCount;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final ListResponse r = new ListResponse();

        public Builder files(List<?> files) {
            r.files = files;
            return this;
        }

        public Builder totalCount(int totalCount) {
            r.totalCount = totalCount;
            return this;
        }

        public ListResponse build() {
            return r;
        }
    }
}
