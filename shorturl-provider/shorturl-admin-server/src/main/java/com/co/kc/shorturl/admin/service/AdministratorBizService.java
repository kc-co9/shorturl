package com.co.kc.shorturl.admin.service;

import org.springframework.stereotype.Service;

/**
 * @author kc
 */
@Service
public class AdministratorBizService {
//    @Autowired
//    private AdministratorRepository administratorRepository;
//
//    public Long getAdministratorIdIfCheckPass(String account, String password) {
//        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
//            throw new ToastException("账号或者密码不能为空");
//        }
//        Administrator administrator = administratorRepository.findByAccount(account)
//                .orElseThrow(() -> new ToastException("账号不存在"));
//        if (!StringUtils.equals(administrator.getPassword(), password)) {
//            throw new ToastException("账号或者密码不正确");
//        }
//        return administrator.getId();
//    }
//
//    public PagingResult<AdministratorListDTO> getAdministratorList(String account, String email, String username, Paging paging) {
//        IPage<Administrator> administratorPage = administratorRepository.page(new Page<>(paging.getPageNo(), paging.getPageSize()), administratorRepository.getQueryWrapper()
//                .eq(StringUtils.isNotBlank(account), Administrator::getAccount, account)
//                .eq(StringUtils.isNotBlank(email), Administrator::getEmail, email)
//                .eq(StringUtils.isNotBlank(username), Administrator::getUsername, username));
//        List<AdministratorListDTO> administratorList = FunctionUtils.mappingList(administratorPage.getRecords(), administrator -> {
//            AdministratorListDTO administratorDTO = new AdministratorListDTO();
//            administratorDTO.setId(administrator.getId());
//            administratorDTO.setAccount(administrator.getAccount());
//            administratorDTO.setUsername(administrator.getUsername());
//            administratorDTO.setEmail(administrator.getEmail());
//            administratorDTO.setCreateTime(administrator.getCreateTime());
//            administratorDTO.setUpdateTime(administrator.getUpdateTime());
//            return administratorDTO;
//        });
//        return PagingResult.<AdministratorListDTO>newBuilder()
//                .paging(paging)
//                .records(administratorList)
//                .totalCount(administratorPage.getTotal())
//                .totalPages(administratorPage.getPages())
//                .build();
//    }
}
