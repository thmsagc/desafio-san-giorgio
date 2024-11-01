package br.com.desafio.application.util;

import br.com.desafio.infrastructure.exception.InvalidArgumentException;
import br.com.desafio.presentation.pageable.PageableParams;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import java.util.ArrayList;
import java.util.List;

public class PageUtils {

    public static Pageable getPageable(PageableParams pageableParams) {
        if(pageableParams.getSort() != null) {
            List<Order> orders = getSortOrders(pageableParams.getSort());
            return PageRequest.of(pageableParams.getPage(), pageableParams.getSize(), Sort.by(orders));
        } else {
            return PageRequest.of(pageableParams.getPage(), pageableParams.getSize());
        }
    }

    public static List<Order> getSortOrders(List<String> sorts) {
        List<Order> orders = new ArrayList<>();

        sorts.forEach(sort -> {
            if(sort.trim().isEmpty()) {
                throw new InvalidArgumentException("Invalid sort property");
            }
            sort = sort.toLowerCase();
            if (sort.contains(",")) {
                String[] propertyOrder = sort.split(",");
                orders.add(new Order(Direction.fromString(propertyOrder[1]), propertyOrder[0]));
            } else {
                orders.add(Order.by(sort));
            }
        });

        return orders;
    }
}
