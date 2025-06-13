package com.co.kc.shortening.infrastructure.mybatis.service;

import com.co.kc.shortening.infrastructure.extension.InfrastructureExtension;
import com.co.kc.shortening.infrastructure.mybatis.entity.CodeGen;
import com.co.kc.shortening.infrastructure.starter.ShortUrlInfrastructureTestApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Rollback
@Transactional
@ExtendWith(InfrastructureExtension.class)
@SpringBootTest(classes = ShortUrlInfrastructureTestApplication.class)
class CodeGenServiceTests {

    @Autowired
    private CodeGenService codeGenService;

    @Test
    void testCreateCodeGen() {
        CodeGen codeGen = codeGenService.newCodeGen();
        Assertions.assertNotNull(codeGen);
        Assertions.assertNotNull(codeGen.getId());
        Assertions.assertEquals(codeGen.getId() * 100, codeGen.getCodeStart().longValue());
        Assertions.assertEquals(((codeGen.getId() + 1) * 100) - 1, codeGen.getCodeEnd().longValue());
    }

    @Test
    void testMultiThreadCreateCodeGen() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        try {
            Supplier<CodeGen> supplier = () -> codeGenService.newCodeGen();
            List<CompletableFuture<CodeGen>> completableFutureList = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                completableFutureList.add(CompletableFuture.supplyAsync(supplier, executorService));
            }
            List<CodeGen> codeGenList =
                    CompletableFuture
                            .allOf(completableFutureList.toArray(new CompletableFuture[0]))
                            .thenApply(v -> completableFutureList.stream().map(CompletableFuture::join).collect(Collectors.toList()))
                            .join();
            Set<Long> idSet = new HashSet<>();
            for (CodeGen codeGen : codeGenList) {
                Assertions.assertTrue(idSet.add(codeGen.getId()));
                Assertions.assertNotNull(codeGen);
                Assertions.assertNotNull(codeGen.getId());
                Assertions.assertEquals(codeGen.getId() * 100, codeGen.getCodeStart().longValue());
                Assertions.assertEquals(((codeGen.getId() + 1) * 100) - 1, codeGen.getCodeEnd().longValue());
            }
        } finally {
            executorService.shutdown();
        }
    }


}
