package com.co.kc.shortening.infrastructure.mybatis.service;

import com.co.kc.shortening.infrastructure.mybatis.entity.CodeGen;
import com.co.kc.shortening.infrastructure.mybatis.mapper.CodeGenMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * key生成器(KeyGen)表服务接口
 *
 * @author makejava
 * @since 2025-03-27 11:21:27
 */
@Service
public class CodeGenService extends BaseService<CodeGenMapper, CodeGen> {

    @Transactional(rollbackFor = Exception.class)
    public CodeGen newCodeGen() {
        CodeGen codeGen = new CodeGen();
        codeGen.setCodeStart(0L);
        codeGen.setCodeEnd(0L);
        save(codeGen);

        codeGen.setCodeStart(codeGen.getId() * 100);
        codeGen.setCodeEnd(((codeGen.getId() + 1) * 100) - 1);

        update(getUpdateWrapper()
                .set(CodeGen::getCodeStart, codeGen.getCodeStart())
                .set(CodeGen::getCodeEnd, codeGen.getCodeEnd())
                .eq(CodeGen::getId, codeGen.getId())
        );

        return codeGen;
    }
}
