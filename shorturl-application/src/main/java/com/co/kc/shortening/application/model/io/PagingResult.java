package com.co.kc.shortening.application.model.io;

import lombok.Getter;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author kc
 */
@Getter
public class PagingResult<T> {
    /**
     * 当前分页
     */
    private Paging paging;

    /**
     * 查询数据列表
     */
    private List<T> records;

    /**
     * 总数量
     */
    private Long totalCount;

    /**
     * 总页数
     */
    private Long totalPages;

    private PagingResult() {
    }

    public <R> PagingResult<R> mapping(Function<T, R> mappingFunc) {
        List<R> mappingRecords =
                Optional.ofNullable(this.getRecords())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(mappingFunc)
                        .collect(Collectors.toList());
        return PagingResult.<R>newBuilder()
                .paging(this.getPaging())
                .records(mappingRecords)
                .totalPages(this.getTotalPages())
                .totalCount(this.getTotalCount())
                .build();
    }

    public static <T> Builder<T> newBuilder() {
        return new Builder<>();
    }

    public static class Builder<T> {
        private final PagingResult<T> pagingResult;

        public Builder() {
            this.pagingResult = new PagingResult<>();
        }

        public Builder<T> paging(Paging paging) {
            this.pagingResult.paging = paging;
            return this;
        }

        public Builder<T> records(List<T> records) {
            this.pagingResult.records = records;
            return this;
        }

        public Builder<T> totalCount(Long totalCount) {
            this.pagingResult.totalCount = totalCount;
            return this;
        }

        public Builder<T> totalPages(Long totalPages) {
            this.pagingResult.totalPages = totalPages;
            return this;
        }

        public PagingResult<T> build() {
            Assert.notNull(this.pagingResult.paging, "分页不能为空");
            Assert.notNull(this.pagingResult.records, "数据不能为空");
            return pagingResult;
        }
    }
}
