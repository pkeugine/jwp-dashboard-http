package nextstep.jwp.web.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class ViewTest {

    @DisplayName("Javascript 경로를 이용하여 View를 생성한다 - 성공")
    @Test
    void createJavascriptView() throws IOException {
        // given
        final URL url = getClass().getClassLoader().getResource("static/assets/chart-area.js");
        final Path path = Paths.get(Objects.requireNonNull(url).getPath());
        final byte[] expected = Files.readAllBytes(path);

        // when
        final View view = new View("/assets/chart-area.js");
        final byte[] actual = view.render().getBytes();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("CSS 경로를 이용하여 View를 생성한다 - 성공")
    @Test
    void createCSSView() throws IOException {
        // given
        final URL url = getClass().getClassLoader().getResource("static/css/styles.css");
        final Path path = Paths.get(Objects.requireNonNull(url).getPath());
        final byte[] expected = Files.readAllBytes(path);

        // when
        final View view = new View("/css/styles.css");
        final byte[] actual = view.render().getBytes();

        // then
        assertThat(actual).isEqualTo(expected);
    }
}