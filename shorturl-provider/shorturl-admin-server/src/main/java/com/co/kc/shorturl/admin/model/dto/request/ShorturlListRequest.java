package com.co.kc.shorturl.admin.model.dto.request;

import com.co.kc.shorturl.common.model.Paging;
import com.co.kc.shorturl.repository.enums.UrlKeyStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author kc
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ShorturlListRequest extends Paging {

    private String key;

    private UrlKeyStatus status;

}
