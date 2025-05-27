package com.co.kc.shortening.infrastructure.client.id.code;

import com.co.kc.shortening.application.client.IdClient;
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

    private final AtomicLong index;
    private final CodeInterval interval;

    public ShortCodeClient(CodeInterval interval) {
        this.interval = interval;
        this.index = new AtomicLong(interval.getCodeStart());
    }

    @Override
    public String next() {
        if (isExhausted()) {
            throw new ExhaustionException("code资源耗尽");
        }
        Long code = index.getAndIncrement();
        if (!interval.contain(code)) {
            throw new ExhaustionException("code资源耗尽");
        }
        return base62(code);
    }

    public boolean isExhausted() {
        Long code = index.get();
        return !interval.contain(code);
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


    //    private CodeGen codeGen;
//    private final CodeGenRepository codeGenRepository;
//
//    public CodeGenService(CodeGenRepository codeGenRepository) {
//        this.codeGenRepository = codeGenRepository;
//    }
//
//    public ShortCode next() {
//        try {
//            if (codeGen.isExhausted()) {
//                synchronized (this) {
//                    codeGen = codeGenRepository.find();
//                    if (codeGen.isExhausted()) {
//                        codeGen = new CodeGen()
//                        codeGenRepository.save(codeGen);
//                    }
//                }
//            }
//            return codeGen.next();
//        } catch (ExhaustionException e) {
//            synchronized (this) {
//                codeGen = codeGenRepository.find();
//                if (codeGen.isExhausted()) {
//                    codeGen = new CodeGen()
//                    codeGenRepository.save(codeGen);
//                }
//            }
//            return codeGen.next();
//        }
//    }
//
//    /**
//     * 查询当前CodeGen
//     *
//     * @return CodeGen
//     */
//    CodeGen findCurrent() {
//        return null;
//    }
//
//    /**
//     * 创建下一个CodeGen
//     * <p>
//     *
//     * @return CodeGen
//     */
//    CodeGen createNext() {
//        return null;
//    }


    @Getter
    @EqualsAndHashCode
    public static class CodeInterval {
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
