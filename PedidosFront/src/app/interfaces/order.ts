import { OrderItem } from "./order-item";

export interface Order {
    orderNumber?: number;
    items?: OrderItem[];
    userId?: number;
    isDelivery?: boolean;
    total?: number;
}
