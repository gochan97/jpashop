package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;


    @GetMapping("/items/album")
    public String createAlbum(Model model){
        model.addAttribute("form", new AlbumForm());
        return "items/createAlbum";
    }

    @PostMapping("/items/album")
    public String create(AlbumForm form){
        Album album = new Album();
        album.setName(form.getName());
        album.setPrice(form.getPrice());
        album.setStockQuantity(form.getStockQuantity());
        album.setArtist(form.getArtist());
        album.setEtc(form.getEtc());
        itemService.saveItem(album);
        return "redirect:/";
    }


    @GetMapping("/items/book")
    public String createForm(Model model){
        model.addAttribute("form", new BookForm()); //html 파일에서 form을 클릭하면 추적할 수 있는 이유는 BookForm()으로 껍데기를 만들어줘서 가능하다
        return "items/createItemForm";
    }

    @PostMapping("/items/book")
    public String create(BookForm form){
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model){
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
        public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){
        Book item = (Book) itemService.findOne(itemId);

        BookForm form = new BookForm();

        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());


        model.addAttribute("form", form); //form을 통해서 데이터가 넘어간다.
        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") BookForm form) { //@ModelAttribute form을하면 html에서 지정했던 form이 그대로 넘어온다.
//들어간 값을 내가 new로 값을 새로 정의해주게 되면 준영속성 컨텍스트이기 때문에 JPA를 실행하면 flush가 변경감지를 하지 못한다.
//이때 변경을 감지 시키기 위해서는 (1. 변경 감지 기능 사용 2. merge 사용) 두 가지 방법이 있다.

//        Book book = new Book();
//        book.setId(form.getId());
//        book.setName(form.getName());
//        book.setPrice(form.getPrice());
//        book.setStockQuantity(form.getStockQuantity());
//        book.setAuthor(form.getAuthor());
//        book.setIsbn(form.getIsbn());

        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());
        //모든 데이터를 가져오는 거 보다는 필요한 데이터만 가져오는게 유지보수 성이 좋다.
        return "redirect:/items";


    }
}
