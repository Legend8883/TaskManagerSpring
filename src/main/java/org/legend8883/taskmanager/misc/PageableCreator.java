package org.legend8883.taskmanager.misc;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class PageableCreator {
    public Pageable assemble(Integer pageSize, Integer pageNum) {
        int pageSizeForPageable = pageSize == null ? 5 : pageSize;
        int pageNumForPageable = pageNum == null ? 0 : pageNum;
        return Pageable
                .ofSize(pageSizeForPageable)
                .withPage(pageNumForPageable);
    }
}
