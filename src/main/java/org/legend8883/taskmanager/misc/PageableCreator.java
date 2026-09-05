package org.legend8883.taskmanager.misc;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Pageable;

@UtilityClass
public final class PageableCreator {
    public static Pageable assemble(
            Integer pageSize,
            Integer pageNum
    ) {
        int pageSizeForPageable = pageSize == null ? 5 : pageSize;
        int pageNumForPageable = pageNum == null ? 0 : pageNum;
        return Pageable
                .ofSize(pageSizeForPageable)
                .withPage(pageNumForPageable);
    }
}
