package com.example;

import com.example.domain.Address;
import com.example.domain.Lender;
import com.example.repository.LenderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class repositoryTest {

    @Autowired
    LenderRepository lenderRepository;
    private Lender lender;
    private Address address;



    @BeforeEach
    void SetUp() {
        byte[] aadharArray=new byte[0];
        byte[] panArray=new byte[0];
        address=new Address("lender address","lender city","7578787","lender state");
        lender=new Lender("lender@lender.com","lender","lender","7567567956","lender","lender",address,"LENDER","LENDER",aadharArray,panArray,2756d,16d,2,"657",2756d,2747d);

    }

    @AfterEach
    void tear() {
        lenderRepository.delete(lender);
        lender=null;
        address=null;
    }


    @Test
    @DisplayName("test case for adding lender success")
    void addLenderShouldReturnAddedLenderSuccess()
    {
        lenderRepository.save(lender);
       Optional<Lender> lenderPresent=lenderRepository.findById(lender.getEmailId());
         assertNotNull(lenderPresent);
         assertEquals("lender@lender.com",lenderPresent.get().getEmailId());
    }



    @Test
    @DisplayName("test case for adding lender failure")
    void addLenderShouldReturnAddedLenderFailure()
    {
        lenderRepository.save(lender);
        Optional<Lender> lenderPresent=lenderRepository.findById(lender.getEmailId());
        assertNotEquals("lender",lenderPresent.get().getEmailId());
    }

    @Test
    @DisplayName("test case for fetching all lender success")
    void getAllLenderSuccess()
    {
        lenderRepository.insert(lender);
        List<Lender> lenderList=lenderRepository.findAll();

        assertEquals(lenderRepository.findAll().size(),lenderList.size());
    }

    @Test
    @DisplayName("test case for fetching all lender failure")
    void getAllLenderFailure()
    {
        lenderRepository.insert(lender);
        List<Lender> lenderList=lenderRepository.findAll();
        assertNotEquals(0,lenderList.size());
    }


    @Test
    @DisplayName("test case for delete lender success")
    void deleteLenderSuccess()
    {
      lenderRepository.insert(lender);
      Optional<Lender> lenderOptional=lenderRepository.findById(lender.getEmailId());
      lenderRepository.delete(lender);
      assertEquals(Optional.empty(),lenderRepository.findById(lender.getEmailId()));

    }

    @Test
    @DisplayName("test case for delete lender failure")
    void deleteLenderFailure()
    {
        lenderRepository.insert(lender);
        Optional<Lender> lenderOptional=lenderRepository.findById(lender.getEmailId());

        assertNotEquals(Optional.empty(),lenderRepository.findById(lender.getEmailId()));

    }




}
