import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.laowang.bean.Emp;
import com.laowang.dao.EmpMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MyTest {

//    @Autowired
//    private SqlSessionFactory factory;//没有注入

//    @Autowired
//    private MybatisSqlSessionFactoryBean factoryBean;//依然没有注入

    private static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
    private EmpMapper empMapper = context.getBean("empMapper",EmpMapper.class);
    /*
    * 从容器中取出SqlSessionFactory实例，测试连接
    * */
    @Test
    public void testConn1() throws Exception {
        DefaultSqlSessionFactory sqlSessionFactory = context.getBean("sqlSessionFactoryBean", DefaultSqlSessionFactory.class);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        System.out.println(sqlSession.getConnection());
    }
    /*
    * 测试不通过，说明实例没装配给接口，实例化交给spring容器了，而容器构建SqlSessionFactory不是下面这样的
    * */
    @Test
    public void testConn2() throws SQLException {
//        SqlSession sqlSession = factory.openSession();
//        System.out.println(sqlSession.getConnection());
    }
    /*测不通
    * */
    @Test
    public void testConn3() throws Exception {
//        SqlSession sqlSession = ((DefaultSqlSessionFactory)(factoryBean.getObject())).openSession();
//        System.out.println(sqlSession.getConnection());

//        SqlSession sqlSession1 = factoryBean.getObject().openSession();
//        System.out.println(sqlSession1.getConnection());
    }
    /*
    * 从容器中获得dataSource实例
    * */
    @Test
    public void testConn4() throws SQLException {
        DruidDataSource dataSource = context.getBean("dataSource", DruidDataSource.class);
        System.out.println(dataSource);
    }

    @Test
    public void test01(){
        EmpMapper empMapper = context.getBean("empMapper", EmpMapper.class);
        Emp emp = new Emp();emp.setEmpno(1004);
        emp.setEname("alalsdjfow");
        int insert = empMapper.insert(emp);
        System.out.println(insert);
    }

    @Test
    public void test02(){
        EmpMapper empMapper = context.getBean("empMapper", EmpMapper.class);
        int i = empMapper.deleteById(1003);
        System.out.println(i);
    }

    @Test
    public void test03(){
        Emp emp = empMapper.selectById(1111);
        System.out.println(emp);
    }

    @Test
    public void test04(){
        List<Emp> emps = empMapper.selectList(null);
    }

    @Test
    public void test05(){
        QueryWrapper<Emp> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("empno",1003);
        int delete = empMapper.delete(queryWrapper);
    }

    @Test
    public void test06(){
        QueryWrapper<Emp> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("sal",1000.00);
        List<Emp> emps = empMapper.selectList(queryWrapper);
        emps.forEach(System.out::println);
    }

    /*
    * 分页配置和连鹏举讲的不一样，新版本不同！
    * */
    @Test
    public void test07(){
        Page<Emp> empPage = empMapper.selectPage(new Page<>(1, 3), null);
        System.out.println(empPage.getRecords());
    }
}
