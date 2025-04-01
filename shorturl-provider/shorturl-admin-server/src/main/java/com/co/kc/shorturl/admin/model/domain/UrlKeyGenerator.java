package com.co.kc.shorturl.admin.model.domain;

import com.co.kc.shorturl.repository.dao.KeyGenRepository;
import com.co.kc.shorturl.repository.po.entity.KeyGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author kc
 */
@Component
public class UrlKeyGenerator {
    private volatile KeyGen keyGen;
    private volatile AtomicLong keyCursor;

    @Autowired
    private KeyGenRepository keyGenRepository;
    @Autowired
    private TransactionTemplate transactionTemplate;

    public Long next() {
        if (keyGen == null) {
            synchronized (this) {
                if (keyGen == null) {
                    keyGen = newKeyGen();
                    keyCursor = new AtomicLong(keyGen.getKeyStart());
                }
            }
        }
        synchronized (this) {
            for (; ; ) {
                Long key = keyCursor.getAndIncrement();
                if (key >= keyGen.getKeyStart() && key <= keyGen.getKeyEnd()) {
                    return key;
                } else {
                    keyGen = newKeyGen();
                    keyCursor = new AtomicLong(keyGen.getKeyStart());
                }
            }
        }
    }

    private KeyGen newKeyGen() {
        return transactionTemplate.execute(status -> {
            KeyGen newKeyGen = new KeyGen();
            newKeyGen.setKeyStart(0L);
            newKeyGen.setKeyEnd(0L);
            keyGenRepository.save(newKeyGen);

            newKeyGen.setKeyStart(newKeyGen.getId() * 100);
            newKeyGen.setKeyEnd(((newKeyGen.getId() + 1) * 100) - 1);

            keyGenRepository.update(keyGenRepository.getUpdateWrapper()
                    .set(KeyGen::getKeyStart, newKeyGen.getKeyStart())
                    .set(KeyGen::getKeyEnd, newKeyGen.getKeyEnd())
                    .eq(KeyGen::getId, newKeyGen.getId())
            );
            return newKeyGen;
        });
    }
}
