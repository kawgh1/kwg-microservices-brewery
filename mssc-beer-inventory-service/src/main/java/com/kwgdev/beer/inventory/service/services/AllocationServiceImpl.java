package com.kwgdev.beer.inventory.service.services;

import com.kwgdev.beer.inventory.service.domain.BeerInventory;
import com.kwgdev.beer.inventory.service.repositories.BeerInventoryRepository;
import com.kwgdev.brewery.model.BeerOrderDto;
import com.kwgdev.brewery.model.BeerOrderLineDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * created by kw on 1/10/2021 @ 10:53 AM
 */

/**
 * This class comes in as a Spring framework service and we do get a hold of the beerInventoryRepository
 * allocateOrder() will loop through each order line when it receives a request,
 * and then attempt to allocate that and check is it a full
 * allocation or a partial allocation?
 *
 *
 */


@Slf4j
@RequiredArgsConstructor
@Service
public class AllocationServiceImpl implements AllocationService {

    private final BeerInventoryRepository beerInventoryRepository;

    @Override
    public Boolean allocateOrder(BeerOrderDto beerOrderDto) {
        log.debug("Allocating OrderId: " + beerOrderDto.getId());

        AtomicInteger totalOrdered = new AtomicInteger();
        AtomicInteger totalAllocated = new AtomicInteger();

        // take the order for each order line, go allocate that and then we're tracking the amount that we've ordered and allocated
        // and if we allocate everything we're happy

        beerOrderDto.getBeerOrderLines().forEach(beerOrderLine -> {
            log.debug(" beerOrderLine quantity = " + String.valueOf(beerOrderLine.getOrderQuantity()));
            log.debug(" beerOrderLine quantity allocated = " + String.valueOf(beerOrderLine.getQuantityAllocated()));
            if ((((beerOrderLine.getOrderQuantity() != null ? beerOrderLine.getOrderQuantity() : 0)
                    - (beerOrderLine.getQuantityAllocated() != null ? beerOrderLine.getQuantityAllocated() : 0)) > 0)) {
                allocateBeerOrderLine(beerOrderLine);
            }
            totalOrdered.set(totalOrdered.get() + beerOrderLine.getOrderQuantity());
            totalAllocated.set(totalAllocated.get() + (beerOrderLine.getQuantityAllocated() != null ? beerOrderLine.getQuantityAllocated() : 0));
        });

        log.debug("Total Ordered: " + totalOrdered.get() + " Total Allocated: " + totalAllocated.get());

        return totalOrdered.get() == totalAllocated.get();
    }

    // go out, get a list of inventory records and because we're gonna have more than one inventory record for a given beer
    // and our service and this is going to go through and update those records so as we draw that beer's inventory down
    // we will delete and remove records if we completely remove that beer's inventory from the repository

    // so we're assigning inventory to the order and at the same time we are removing inventory from our database
    private void allocateBeerOrderLine(BeerOrderLineDto beerOrderLine) {
        List<BeerInventory> beerInventoryList = beerInventoryRepository.findAllByUpc(beerOrderLine.getUpc());

        beerInventoryList.forEach(beerInventory -> {
            int inventory = (beerInventory.getQuantityOnHand() == null) ? 0 : beerInventory.getQuantityOnHand();
            int orderQty = (beerOrderLine.getOrderQuantity() == null) ? 0 : beerOrderLine.getOrderQuantity();
            int allocatedQty = (beerOrderLine.getQuantityAllocated() == null) ? 0 : beerOrderLine.getQuantityAllocated();
            int qtyToAllocate = orderQty - allocatedQty;

            if (inventory >= qtyToAllocate) { // full allocation
                inventory = inventory - qtyToAllocate;
                beerOrderLine.setQuantityAllocated(orderQty);
                beerInventory.setQuantityOnHand(inventory);

                beerInventoryRepository.save(beerInventory);
            } else if (inventory > 0) { //partial allocation
                beerOrderLine.setQuantityAllocated(allocatedQty + inventory);
                beerInventory.setQuantityOnHand(0);

            }

            if (beerInventory.getQuantityOnHand() == 0) {
                beerInventoryRepository.delete(beerInventory);
            }
        });

    }

    @Override
    public void deallocateOrder(BeerOrderDto beerOrderDto) {
        beerOrderDto.getBeerOrderLines().forEach(beerOrderLineDto -> {
            BeerInventory beerInventory = BeerInventory.builder()
                    .beerId(beerOrderLineDto.getBeerId())
                    .upc(beerOrderLineDto.getUpc())
                    .quantityOnHand(beerOrderLineDto.getQuantityAllocated())
                    .build();

            BeerInventory savedInventory = beerInventoryRepository.save(beerInventory);

            log.debug("Saved Inventory for beer upc: " + savedInventory.getUpc() + " inventory id: " + savedInventory.getId());
        });
    }

//    @Override
//    public void deallocateOrder(BeerOrderDto beerOrderDto) {
//        log.debug("Deallocating OrderId: " + beerOrderDto.getId());
//
//        beerOrderDto.getBeerOrderLines().forEach(beerOrderLineDto -> {
//            BeerInventory beerInventory = beerInventoryRepository.getOne(beerOrderLineDto.getId());
//            int existingQtyOnHand = beerInventory.getQuantityOnHand();
//            existingQtyOnHand += beerOrderLineDto.getQuantityAllocated();
//            beerInventory.setQuantityOnHand(existingQtyOnHand + beerOrderLineDto.getQuantityAllocated());
//            beerInventoryRepository.saveAndFlush(beerInventory);
//
//            // this is the code from the course.
//            //
//            //            BeerInventory beerInventory = BeerInventory.builder()
//            //                    .beerId(beerOrderLineDto.getId())
//            //                    .upc(beerOrderLineDto.getUpc())
//            //                    // give back the inventory
//            //                    // seems like this won't work?.  Seems like we need to increment that value...
//            //                    .quantityOnHand(beerOrderLineDto.getQuantityAllocated())
//            //                    .build();
//            BeerInventory savedInventory = beerInventoryRepository.save(beerInventory);
//
//            log.debug("Saved Inventory for beer upc: " + savedInventory.getUpc() + " inventory id: " + savedInventory.getId());
//        });
//    }
}
