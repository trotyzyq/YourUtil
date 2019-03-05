import org.junit.Test;

/**
 * 继承测试
 * @author trotyzyq
 */

public class JunitExtendTest extends AbstrachTest{

    @Override
    public void init() {
        System.out.println("init");
    }

    @Test
    public void test(){
        System.out.println(11);
    }
}
