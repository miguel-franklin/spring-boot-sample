package nl.revolution.example.models;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class PessoaTest {

    @Test
    public void testCalculo() {
        Pessoa p = new Pessoa();
        Assert.assertEquals(p.calculo(1, 3), 3);
    }
}
