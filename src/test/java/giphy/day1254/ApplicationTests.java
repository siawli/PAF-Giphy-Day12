package giphy.day1254;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import giphy.day1254.service.GiphyService;

@SpringBootTest
class ApplicationTests {

	@Autowired
	private GiphyService giphySvc;

	@Test
	void shouldLoad10Images() {
		List<String> gifs = giphySvc.getGiphs("dog");
		assertEquals(10, gifs.size(), "Default number of gif = 10");
	}

}
