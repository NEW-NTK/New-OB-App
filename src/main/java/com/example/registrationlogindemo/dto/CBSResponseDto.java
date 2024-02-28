package com.example.registrationlogindemo.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CBSResponseDto {

    private String GRP_REF_NO;
    private String TRN_REF_NO;
    private String EVENT_SR_NO;
    private String EVENT;
    private String SOURCE_CODE;
    private String TXN_BRANCH;
    private String EXT_REF_NO_REQ;
    private String TRN_REF_NO_DAILY_LOG;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date TXN_DT;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date VAL_DT;

    private String TXN_TIMESTAMP;
    private String ACTION_INDICATOR;
    private String REFERRAL_ALLOWED;
    private String REFERRAL_STATUS;
    private String MAKER_ID;
    private String CHECKER_ID;
    private String STATUS;

    // Getter and Setter methods
}
