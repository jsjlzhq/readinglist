package readinglist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Created by zhiqin.li on 2018/11/28.
 */

@Controller //组件扫描会自动将其注册为Spring应用程序上下文里的一个Bean
@RequestMapping("readingList")
public class ReadingListController {

    private ReadingListRepository readingListRepository;

    @Autowired
    public ReadingListController(ReadingListRepository readingListRepository) {
        this.readingListRepository = readingListRepository;
    }

//    @RequestMapping(value = "/{reader}", method = RequestMethod.GET)
//    public String readersBooks(
//        @PathVariable("reader") String reader,
//        Model model) {
//        List<Book> readingList = readingListRepository.findByReader(reader);
//        if(readingList != null) {
//            model.addAttribute("books", readingList);
//        }
//        return "readingList";
//    }

    @RequestMapping(value="/search", method = RequestMethod.GET)
    public String searchBooks(
            @RequestParam Map<String, Object> map,
            ModelMap model) {
        System.out.println("author and reader");

        String author="";
        String reader="";
        if (map.containsKey("author")) {
            author = map.get("author").toString();
        }
        if(map.containsKey("reader")) {
            reader = map.get("reader").toString();
        }

        List<Book> readingList;
        if(!author.isEmpty() && !reader.isEmpty()) {
            readingList = readingListRepository.findByReaderAndAuthor(reader, author);
        }
        else if(!author.isEmpty()) {
            readingList = readingListRepository.findByAuthor(author);
        }
        else if(!reader.isEmpty()){
            readingList = readingListRepository.findByReader(reader);
        }
        else {
            readingList = readingListRepository.findAll();
        }

        if(readingList != null)
        {
            model.addAttribute("books", readingList);
        }
        return "readingList";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String addToReadingList(Book book) {
        readingListRepository.save(book);
        return "redirect:/readingList/search?";
    }
}
