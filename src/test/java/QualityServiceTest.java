import com.idealista.application.Mapper;
import com.idealista.application.QualityService;
import com.idealista.infrastructure.api.PublicAd;
import com.idealista.infrastructure.api.QualityAd;
import com.idealista.infrastructure.persistence.AdVO;
import com.idealista.infrastructure.persistence.InMemoryPersistence;
import com.idealista.infrastructure.persistence.PictureVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QualityServiceTest {

  //id 1 -> Description 05 + keyWords 00 + Description length 20 + pictures -10 + Complete 00  ===  15
  //id 2 -> Description 05 + keyWords 05 + Description length 30 + pictures  20 + Complete 40  ===  100
  //id 3 -> Description 00 + keyWords 00 + Description length 00 + pictures  20 + Complete 00  ===  20
  //id 4 -> Description 05 + keyWords 05 + Description length 30 + pictures  10 + Complete 40  ===  90
  //id 5 -> Description 05 + keyWords 00 + Description length 00 + pictures  30 + Complete 40  ===  75
  //id 6 -> Description 00 + keyWords 00 + Description length 00 + pictures  10 + Complete 40  ===  50
  //id 7 -> Description 05 + keyWords 00 + Description length 00 + pictures -10 + Complete 00  === -05
  //id 8 -> Description 05 + keyWords 00 + Description length 20 + pictures  20 + Complete 00  ===  45

  private List<AdVO> ads = new ArrayList<>();
  private List<PictureVO> pictures;
  private List<QualityAd> qualityAds = new ArrayList<>();
  private List<PublicAd> publicAds = new ArrayList<>();
  @Mock
  private InMemoryPersistence persistence;
  @Mock
  private Mapper mapper;
  @InjectMocks
  private QualityService service;

  public QualityServiceTest() {
    ads.add(new AdVO(1, "CHALET", "Este piso es una ganga, compra, compra, COMPRA!!!!!", Collections.emptyList(), 300, null, null, null));
    ads.add(new AdVO(2, "FLAT", "Nuevo ático céntrico recién reformado. No deje pasar la oportunidad y adquiera este ático de lujo", Collections.singletonList(4), 300, null, null, null));
    ads.add(new AdVO(3, "CHALET", "", Collections.singletonList(2), 300, null, null, null));
    ads.add(new AdVO(4, "FLAT", "Ático céntrico muy luminoso y recién reformado, parece nuevo", Collections.singletonList(5), 300, null, null, null));
    ads.add(new AdVO(5, "FLAT", "Pisazo,", Arrays.asList(3, 8), 300, null, null, null));
    ads.add(new AdVO(6, "GARAGE", "", Collections.singletonList(6), 300, null, null, null));
    ads.add(new AdVO(7, "GARAGE", "Garaje en el centro de Albacete", Collections.emptyList(), 300, null, null, null));
    ads.add(new AdVO(8, "CHALET", "Maravilloso chalet situado en lAs afueras de un pequeño pueblo rural. El entorno es espectacular, las vistas magníficas. ¡Cómprelo ahora!", Arrays.asList(1, 7), 300, null, null, null));

    pictures = new ArrayList<>();
    pictures.add(new PictureVO(1, "http://www.idealista.com/pictures/1", "SD"));
    pictures.add(new PictureVO(2, "http://www.idealista.com/pictures/2", "HD"));
    pictures.add(new PictureVO(3, "http://www.idealista.com/pictures/3", "SD"));
    pictures.add(new PictureVO(4, "http://www.idealista.com/pictures/4", "HD"));
    pictures.add(new PictureVO(5, "http://www.idealista.com/pictures/5", "SD"));
    pictures.add(new PictureVO(6, "http://www.idealista.com/pictures/6", "SD"));
    pictures.add(new PictureVO(7, "http://www.idealista.com/pictures/7", "SD"));
    pictures.add(new PictureVO(8, "http://www.idealista.com/pictures/8", "HD"));

    qualityAds.add(new QualityAd(2, "FLAT", "Nuevo ático céntrico recién reformado. No deje pasar la oportunidad y adquiera este ático de lujo", Collections.singletonList("http://www.idealista.com/pictures/4"), 300, null, 100, null));
    qualityAds.add(new QualityAd(4, "FLAT", "Ático céntrico muy luminoso y recién reformado, parece nuevo", Collections.singletonList("http://www.idealista.com/pictures/5"), 300, null, 90, null));
    qualityAds.add(new QualityAd(5, "FLAT", "Pisazo,", Arrays.asList("http://www.idealista.com/pictures/3", "http://www.idealista.com/pictures/8"), 300, null, 75, null));
    qualityAds.add(new QualityAd(6, "GARAGE", "", Collections.singletonList("http://www.idealista.com/pictures/6"), 300, null, 50, null));
    qualityAds.add(new QualityAd(8, "CHALET", "Maravilloso chalet situado en lAs afueras de un pequeño pueblo rural. El entorno es espectacular, las vistas magníficas. ¡Cómprelo ahora!", Arrays.asList("http://www.idealista.com/pictures/1", "http://www.idealista.com/pictures/7"), 300, null, 45, null));
    qualityAds.add(new QualityAd(3, "CHALET", "", Collections.singletonList("http://www.idealista.com/pictures/2"), 300, null, 20, null));
    qualityAds.add(new QualityAd(1, "CHALET", "Este piso es una ganga, compra, compra, COMPRA!!!!!", Collections.emptyList(), 300, null, 15, null));
    qualityAds.add(new QualityAd(7, "GARAGE", "Garaje en el centro de Albacete", Collections.emptyList(), 300, null, -5, null));

    publicAds.add(new PublicAd(2, "FLAT", "Nuevo ático céntrico recién reformado. No deje pasar la oportunidad y adquiera este ático de lujo", Collections.singletonList("http://www.idealista.com/pictures/4"), 300, null));
    publicAds.add(new PublicAd(4, "FLAT", "Ático céntrico muy luminoso y recién reformado, parece nuevo", Collections.singletonList("http://www.idealista.com/pictures/5"), 300, null));
    publicAds.add(new PublicAd(5, "FLAT", "Pisazo,", Arrays.asList("http://www.idealista.com/pictures/3", "http://www.idealista.com/pictures/8"), 300, null));
    publicAds.add(new PublicAd(6, "GARAGE", "", Collections.singletonList("http://www.idealista.com/pictures/6"), 300, null));
    publicAds.add(new PublicAd(8, "CHALET", "Maravilloso chalet situado en lAs afueras de un pequeño pueblo rural. El entorno es espectacular, las vistas magníficas. ¡Cómprelo ahora!", Arrays.asList("http://www.idealista.com/pictures/1", "http://www.idealista.com/pictures/7"), 300, null));
  }

  @Test
  public void givenAdList_ShouldReturn_correctScore() {
    when(persistence.getAds()).thenReturn(ads);
    when(persistence.getPictures()).thenReturn(pictures);
    when(mapper.toQualityAd(any())).thenCallRealMethod();

    assertThat(service.getQualityListing())
      .usingElementComparatorIgnoringFields("irrelevantSince")
      .isEqualTo(qualityAds);
  }

  @Test
  public void givenAdList_ShouldReturn_publicAds() {
    when(persistence.getAds()).thenReturn(ads);
    when(persistence.getPictures()).thenReturn(pictures);
    when(mapper.toQualityAd(any())).thenCallRealMethod();
    when(mapper.toPublicAd(any())).thenCallRealMethod();

    assertThat(service.getPublicListing())
      .isEqualTo(publicAds);
  }
}
