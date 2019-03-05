import org.junit.After;
import org.junit.Before;

/**
 * @author zyq
 * 抽象测试类
 */
public abstract class AbstrachTest {
    public abstract void init();

    @Before
    public void before(){
        System.out.println("before");
        init();
    }

    @After
    public void after(){
        System.out.println("after");
    }
}
