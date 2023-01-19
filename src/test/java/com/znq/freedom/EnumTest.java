package com.znq.freedom;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EnumTest {

    public String judge(String roleName) {
        // 一行代码搞定！之前的if/else没了！
        return RoleEnum.valueOf(roleName).op();
    }

    interface RoleOperation {
        String op(); // 表示某个角色可以做哪些op操作
    }

    enum RoleEnum implements RoleOperation {
        // 系统管理员(有A操作权限)
        ROLE_ROOT_ADMIN {
            @Override
            public String op() {
                return "ROLE_ROOT_ADMIN:" + " has AAA permission";
            }
        },

        // 订单管理员(有B操作权限)
        ROLE_ORDER_ADMIN {
            @Override
            public String op() {
                return "ROLE_ORDER_ADMIN:" + " has BBB permission";
            }
        },

        // 普通用户(有C操作权限)
        ROLE_NORMAL {
            @Override
            public String op() {
                return "ROLE_NORMAL:" + " has CCC permission";
            }
        };
    }

    @Test
    public void test() {
        System.out.println(judge("ROLE_ROOT_ADMIN"));
    }
}
