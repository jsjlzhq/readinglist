package readinglist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by zhiqin.li on 2018/11/28.
 */
public interface ReadingListRepository extends JpaRepository<Book, Long> {

    List<Book> findByReader(String reader);
    List<Book> findByAuthor(String author);
    List<Book> findByReaderAndAuthor(String reader, String author);

}
