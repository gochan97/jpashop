package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final MemberService memberService;
    private final OrderService orderService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model){
        List<Member> members = memberService.findMembers(); //members로 memberService를 전부 가져온다.
        List<Item> items = itemService.findItems();         //items로 itemService를 전부 가져온다.

        model.addAttribute("members", members);
        model.addAttribute("items", items);
        //모델에 담어서 orderForm으로 넘겨준다.
        return "order/orderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("items") Long itemId,
                        @RequestParam("count") int count){
        orderService.order(memberId,itemId,count); //핵심 비지니스 로직은 위에처럼 식별자만 남겨주고 OrderService 로직에서
        //처리하는게 더 깔끔하고 수정 시에도 영속 상태를 유지할 수 있다.

        return "redirect:/orders";

    }
}
