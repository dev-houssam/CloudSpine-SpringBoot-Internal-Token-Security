@RestController
@RequestMapping("/orders")
public class OrdersController {

    private final OrderService orderService;

    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getOrders(
            @AuthenticationPrincipal InternalAuthentication auth
    ) {

        InternalPrincipal principal =
                (InternalPrincipal) auth.getPrincipal();

        String userId = principal.userId();
        String tenant = principal.tenantId();

        return orderService.findOrders(userId, tenant);
    }
}