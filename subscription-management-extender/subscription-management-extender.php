<?php
/**
 * Plugin Name: Subscription Management Extender
 * Plugin URI: [URI]
 * Description: Extends the order statuses and introduces VOD & Live TV subscription tracking for Woo Subscriptions.
 * Version: 1.0.0
 * Author: Gevorg Sahakyan
 * Author URI: [URI]
 */

include_once ( __DIR__ . '/class-cf-status-extender.php' );
include_once ( __DIR__ . '/class-cf-early-renew.php' );

//
// Status Extender
// 
add_action( 'woocommerce_shop_subscription_list_table_custom_column', 'all_subscriptions_page_admin', 10, 2);
$IS_CSS_ECHOED = array(
    'order_pseudo_button_CSS' => false,
    'sub_pseudo_button_CSS'   => false
);
function all_subscriptions_page_admin( $column_name, $subscription ) {
    if ( $column_name == 'status' ) {
        $subscription_data = CF_Status_Extender::get_subscription_data( $subscription );
    
        echo CF_Status_Extender::get_table_CSS( $subscription_data );

        global $IS_CSS_ECHOED;
        if ( ! $IS_CSS_ECHOED['sub_pseudo_button_CSS'] ) {
            $IS_CSS_ECHOED['sub_pseudo_button_CSS'] = true;
            echo CF_Status_Extender::get_sub_pseudo_button_CSS();
        }
    }
}

add_action( 'woocommerce_shop_order_list_table_custom_column', 'all_orders_page_admin', 20, 2 );
function all_orders_page_admin( $column_name, $order ) {
    if ( $column_name == 'subscription_relationship' ) {
        foreach ( $order->get_items() as $item ) {
            if ( ! $item->get_product()->is_virtual() ) {
                echo <<<EOT
                    <p style="margin-top: 5px !important;">Contains Physical</p>
                EOT;
                break;
            }
        }
    }

    if ( $column_name === 'order_number' ) {
        $order_data = CF_Status_Extender::get_order_data( $order );
        if ( $order_data ) {
            echo CF_Status_Extender::get_table_CSS( $order_data );
        }
        
        global $IS_CSS_ECHOED;
        if ( ! $IS_CSS_ECHOED['order_pseudo_button_CSS'] ) {
            $IS_CSS_ECHOED['order_pseudo_button_CSS'] = true;
            echo CF_Status_Extender::get_order_pseudo_button_CSS();
        }
    }
}

add_filter( 'wcs_admin_subscription_related_orders_to_display', 'related_orders_section', 999, 3 );
function related_orders_section( $orders_to_display, $subscriptions, $this_order ) {

    wcs_sort_objects( $orders_to_display, 'date_created', 'ascending' );

    $json = array();

    $order_priority = 0;
    foreach ( $orders_to_display as $order ) {
        if ( $order->get_id() == $this_order->get_id() ) {
            continue;
        }

        if ( wcs_is_subscription( $order ) ) {
            $json[] = CF_Status_Extender::get_subscription_data( $order );
            continue;
        }

        $data = CF_Status_Extender::get_order_data( $order, true );

        $order_data = $data[0];
        $is_high_priority = $data[1];

        if ( $is_high_priority && $order_priority == 0 ) {
            $order_data['secondary_color'] = CF_Status_Extender::$colors['purple_primary'];
            $order_priority++;
        } else if ( $order_priority > 0 ) {
            $order_data['secondary_color'] = CF_Status_Extender::$colors['purple_secondary'];
            $order_priority++;
        }

        $json[] = $order_data;
    }
    
    $standalone_element_data = array(
        'order_type_text' => null,
        'primary_color'   => null,
        'css_id'          => null,
        'css_styles'      => ''
    );

    $standalone_element_data['css_id'] = 'order_data';
    $standalone_element_data['order_type_text'] = CF_Status_Extender::get_order_type( $this_order );
    $standalone_element_data['primary_color'] = CF_Status_Extender::get_item_primary_color( $this_order );

    echo CF_Status_Extender::get_sub_pseudo_button_CSS();
    echo CF_Status_Extender::get_order_pseudo_button_CSS();

    $standalone_element_data = json_encode( $standalone_element_data );
    $json = json_encode( $json );

    echo <<<EOT
        <script type="text/javascript">
            window.addEventListener('load', function () {
                var orderAnchorNodes = document.querySelectorAll('a[href*="&id="]'); 
                
                ordersData = $json;
                standalone_element_data = $standalone_element_data;

                if ( standalone_element_data != null) {
                    var standalone_element = document.querySelector('#' + standalone_element_data["css_id"]);
                    standalone_element.style.cssText += standalone_element_data['css_styles'];

                    var orderDetailsHeading = document.querySelector('#order_data h2');
                    // orderDetailsHeading.style.cssText += 'text-shadow: none !important;';

                    var orderType = document.createElement('h2');
                    orderType.textContent = standalone_element_data['order_type_text'];
                    orderType.style.float = 'right';
                    orderType.style.textShadow = 'none';
                    orderType.style.padding = '8px';
                    orderType.style.borderRadius = '7px';
                    orderType.style.backgroundColor = standalone_element_data['primary_color'];
                    orderType.style.border = '1px grey solid';

                    orderDetailsHeading.parentNode.insertBefore(orderType, orderDetailsHeading);
                }
                
                var match;
                for (const element of ordersData) {
                    for ( const node of orderAnchorNodes ) {
                        if ( node.parentNode.tagName != 'TD' ) {
                            continue;
                        }

                        match = node.href.match(/&id=(\d+)/)[1];

                        if ( element['id'] == match ) {
                            if ( element['is_subscription'] === true ) {
                                node.parentNode.parentNode.style.cssText += 'box-shadow: inset 0px 0px 30px 0px ' + element['sub_color'] + ' ;';
                                continue;
                            }
                            node.parentNode.style.cssText += 'background-color: ' + element['primary_color'] + ' !important;';
                            node.parentNode.parentNode.style.cssText += 'background-color: ' + element['secondary_color'] + ' !important;';
                        }
                    }
                }
            });
        </script>
    EOT;

    return $orders_to_display;
}

add_action( 'woocommerce_admin_order_data_after_order_details', 'editable_order_meta_general', 999, 1 );
function editable_order_meta_general( $order ) {

    if ( ! CF_Status_Extender::order_contains_virtual_product( $order ) ) {
        return;
    }

    if ( wcs_is_subscription( $order ) ) {
        $field_name = 'Service Status';

        $result = CF_Status_Extender::get_subscription_meta_options( $order );

        $input_options = $result[0];
        $order_status_label = $result[1];
    } else {
        $order_status = $order->get_status();
        
        if ( $order_status == 'on-hold' || $order_status == 'failed' || $order_status == 'pending' || $order_status == 'draft' ) {
            return;
        }

        $field_name = 'Order Status';
        
        $result = CF_Status_Extender::get_order_meta_options( $order );
        $input_options = $result[0];
        $order_status_label = $result[1];
    }

	?>
		<br class="clear" />
		<h3>Custom Fields
             <a href="#" class="edit_address">Edit</a> 
        </h3>
		<div class="address">
			<p>
                <strong> 
                    <?php echo $field_name; ?>
                </strong>
                <?php echo $order_status_label; ?>
            </p>
		</div>
		<div class="edit_address">
			<?php
				woocommerce_wp_select( $input_options );
			?>
		</div>
	<?php 
}

add_action( 'woocommerce_process_shop_order_meta', 'save_CF_metas', 999, 2 );
function save_CF_metas( $order_id, $order ) {
    if ( ! CF_Status_Extender::order_contains_virtual_product( $order ) ) {
        return;
    }

    if ( wcs_is_subscription( $order ) ) {
        $input_id = 'cf-subscription-status';
        $meta_key = 'CF_service_status';
    } else if ( wcs_is_order( $order ) ) {
        $input_id = 'cf-order-status';
        $meta_key = 'CF_order_status';
    }

    if ( $_POST[ $input_id ] !== null ) {
        $order = wc_get_order( $order_id );
        $order->update_meta_data( $meta_key, wc_clean( $_POST[ $input_id ] ) );
        $order->save();
    }
}

// Small modifications...
/*
 *
 * Autocomplete both Renewals and Early Renewals...
 * 
 */
add_action( 'woocommerce_subscription_renewal_payment_complete', 'auto_complete_renewals', 999, 2 );
function auto_complete_renewals( $subscription, $last_order ) {
    if ( $last_order->get_status() != 'processing' ) {
        return;
    }

    if ( wcs_order_contains_early_renewal( $last_order ) ) {
        $last_order->update_status( 'completed', '[CF] Auto Completed Early Renewal.' );
    } else if ( wcs_order_contains_renewal( $last_order ) ) {
        $last_order->update_status( 'completed', '[CF] Auto Completed Renewal.' );
    }

    $last_order->update_meta_data( 'CF_renew_auto_completed', 'yes' );
    $last_order->save();
}

/*
 *
 * Do not allow the customer option to reactivate Subscription if the Subscription
 * was put On-Hold manually by Admin...
 * 
 */
add_filter( 'wcs_view_subscription_actions', 'custom', 10, 3);
function custom( $actions, $subscription, $user_id ) {
    if ( $subscription->get_status() == 'on-hold' && ! $subscription->needs_payment() ) {
        unset( $actions['reactivate'] );
    }

    return $actions;
}

/*
 *
 * For switch orders that have $0.00 for a fee, change Completed to Processing 
 * *The orders should be marked processing, don't know whats the issue but need to contact support...
 * 
 */
add_action( 'woocommerce_subscriptions_switch_completed', 'change_completed_to_processing_switch_order', 999, 1 );
function change_completed_to_processing_switch_order( $order ) {
    if ( wcs_order_contains_subscription( $order, 'switch' ) && ( $order->get_total() == 0 ) && $order->has_status( 'completed' ) ) {
        $order->update_status( 'processing', '[CF] Auto Changed Status from Completed to Processing for $0.00 order.' );
        $order->save();
    }
 }
