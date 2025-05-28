package com.co.kc.shortening.infrastructure.client.id.code;

import com.co.kc.shortening.application.client.IdClient;
import com.co.kc.shortening.infrastructure.mybatis.entity.CodeGen;
import com.co.kc.shortening.infrastructure.mybatis.service.CodeGenService;
import com.co.kc.shorturl.common.exception.ExhaustionException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author kc
 */
public class ShortCodeClient implements IdClient<String> {
    private static final char[] CHARSET = new char[]{
            'e', 'H', 'M', 'F', 'x', 'X', 'm', 'l', '4',
            'L', 'g', '6', '1', 'B', 'n', 'C', 't', 'q',
            'w', '2', 'a', 'o', 'K', 'P', 'W', 'R', 'S',
            'A', 'k', '0', 'U', 'Y', '9', 's', '5', 'c',
            'N', 'Q', 'T', 'r', 'I', 'h', 'V', 'b', 'E',
            '3', '7', 'D', 'i', 'O', 'z', 'G', 'u', 'p',
            'f', 'j', 'v', '8', 'd', 'Z', 'y', 'J'};

    private Long codeIdx;
    private CodeInterval interval;
    private final CodeGenService codeGenService;

    public ShortCodeClient(CodeGenService codeGenService) {
        this.codeGenService = codeGenService;
    }

    @Override
    public synchronized String next() {
        if (isExhausted()) {
            CodeGen codeGen = codeGenService.newCodeGen();
            this.interval = new CodeInterval(codeGen.getCodeStart(), codeGen.getCodeEnd());
            this.codeIdx = interval.getCodeStart();
        }
        return base62(codeIdx++);
    }

    public boolean isExhausted() {
        if (codeIdx == null) {
            return true;
        }
        return !interval.contain(codeIdx);
    }

    private String base62(Long num) {
        // 定义62进制的字符集
        if (num == 0) {
            return String.valueOf(CHARSET[0]);
        }
        StringBuilder result = new StringBuilder();
        while (num > 0) {
            // 取模得到当前位的索引
            int remainder = (int) (num % 62);
            // 根据索引从字符集中获取对应的字符
            result.insert(0, CHARSET[remainder]);
            // 整除62更新十进制数
            num = num / 62;
        }
        return result.toString();
    }

    @Getter
    @EqualsAndHashCode
    private static class CodeInterval {
        /**
         * code开始
         */
        private final Long codeStart;
        /**
         * code结束
         */
        private final Long codeEnd;

        public CodeInterval(Long codeStart, Long codeEnd) {
            if (codeStart == null) {
                throw new IllegalArgumentException("codeStart is null");
            }
            if (codeEnd == null) {
                throw new IllegalArgumentException("codeEnd is null");
            }
            if (codeStart > codeEnd) {
                throw new IllegalArgumentException("codeEnd is less than codeStart");
            }
            this.codeStart = codeStart;
            this.codeEnd = codeEnd;
        }

        public boolean contain(Long code) {
            return code >= codeStart && code <= codeEnd;
        }
    }


}
