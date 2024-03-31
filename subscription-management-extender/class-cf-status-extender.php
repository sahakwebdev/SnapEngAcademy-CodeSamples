<?php


if ( ! defined( 'ABSPATH' ) ) {
	exit; // Exit if accessed directly.
}

class CF_Status_Extender {
    public static $colors = array(
        // used in [View Subscriptions] <Admin> page
        'red_primary'              => '#FCBBBB',
        'red_secondary'            => '#F9DADA',
        'red_border'               => '#FF9393',
        'green_primary'            => '#C6E1C6',
        'green_secondary'          => '#E9F4E9',
        'green_border'             => '#92B473',
        'purple_primary'           => '#DEB5DE',
        'purple_secondary'         => '#EED9EE',
        'purple_border'            => 'transparent',
        'brown_primary'            => '#D9A26D',
        'brown_secondary'          => '#EDD2B8',
        'brown_border'             => 'transparent',
        'yellow_primary'           => '#FFEE65',
        'yellow_secondary'         => '#FFF7B5',
        'yellow_border'            => '#DDC600',
        // used in [Edit Order] <Admin> page
        'order-completed'          => '#a2b6c4',
        'order-processing'         => '#92B473',
        'order-pending-payment'    => '#9b9b9b',
        'order-on-hold'            => '#d2ae69',
        'order-failed'             => '#d57575',
        'order-cancelled'          => '#b0b0b0',
        'order-refunded'           => '#b0b0b0',
        'order-draft'              => '#b0b0b0',
        // used in [Edit Subscription] <Admin> page
        'sub-active'               => '#92B473',
        'sub-on-hold'              => '#d2ae69',
        'sub-pending-cancellation' => '#9b9b9b',
        'sub-cancelled'            => '#b0b0b0',
        'sub-expired'              => '#724663',
        // subscription colors when shown in Related Orders section
        'green_sub'                => '#168C16',
        'red_sub'                  => '#ff1f1f',
    );

    private static $item_data = array(
		'id'              => null,
        'is_subscription' => false,
        'is_order'        => false,
		'primary_color'   => 'white',
		'secondary_color' => 'white',
        'border_color'    => 'white',
        'sub_color'       => null,
        'css_class'       => null,
        'css_id'          => null
	);

    public static function order_contains_virtual_product( $order ) {
        foreach ( $order->get_items() as $item ) {
            if ( $item->get_product()->is_virtual() ) {
                return true;
            }
        }

        return false;
    }

    public static function get_order_data( $order, $check_high_priority = false, $first_high_priority = false ) {
        $colors = self::$colors;
		$order_data = self::$item_data;

		$order_data['id'] = $order->get_id();
        $order_status = $order->get_status();

        $order_data['primary_color']   = $colors['red_primary'];
        $order_data['secondary_color'] = $colors['red_secondary'];
        $order_data['border_color']    = $colors['red_border'];
        $is_high_priority = true;
        // if ( $check_high_priority ) {
        //     $order_data['secondary_color'] = $colors['purple_secondary'];
        // }

        if ( $order_status == 'draft' || $order_status == 'refunded' ) {
            return false;
        } 
        
        if ( $order_status == 'on-hold' || $order_status == 'failed' || $order_status == 'pending' ) {
            $order_data['primary_color']   = $colors['yellow_primary'];
            $order_data['secondary_color'] = $colors['yellow_secondary'];
            $order_data['border_color']    = $colors['yellow_border'];
        } else if ( $order_status == 'completed' && self::is_order_processed( $order ) ) {
            $order_data['primary_color']   = $colors['green_primary'];
			$order_data['secondary_color'] = $colors['green_secondary'];
            $order_data['border_color']    = $colors['green_border'];
            $is_high_priority = false;
        } else if ( $order_status == 'cancelled' && self::is_order_not_processed( $order ) ) {
            $order_data['primary_color']   = $colors['green_primary'];
			$order_data['secondary_color'] = $colors['green_secondary'];
            $order_data['border_color']    = $colors['green_border'];
            $is_high_priority = false;
        }        
        
        // if ( $first_high_priority ) {
        //     $order_data['secondary_color'] = $colors['purple_primary'];
        // }

        if ( $check_high_priority ) {
            return array( $order_data, $is_high_priority );
        }

        return $order_data;
    }

    public static function has_unresolved_orders( $subscription ) {
        $colors = self::$colors;

        $related_orders = $subscription->get_related_orders( 'all', array( 'parent', 'renewal', 'switch' ) );
    
        foreach ( $related_orders as $order ) {
            $order_data = self::get_order_data( $order );
            if ( $order_data === false ) {
                continue;
            }
            if ( ( $order_data['primary_color'] == $colors['red_primary'] ) || ( $order_data['primary_color'] == $colors['yellow_primary'] ) ) {
                return true;
            }
        }

        return false;
    }

    public static function get_subscription_data( $subscription ) {
        $colors = self::$colors;
        $subscription_data = self::$item_data;

        $sub_status = $subscription->get_status();

        $subscription_data['id'] = $subscription->get_id();

        $subscription_data['is_subscription'] = true;

        $subscription_data['primary_color']   = $colors['red_primary'];
        $subscription_data['secondary_color'] = $colors['red_secondary'];
        $subscription_data['border_color']    = $colors['red_border'];
        $subscription_data['sub_color']       = $colors['red_sub'];

        if ( $sub_status == 'active' || $sub_status == 'pending-cancel' ) {
            if ( self::is_service_active( $subscription ) ) {
                $subscription_data['primary_color']   = $colors['green_primary'];
                $subscription_data['secondary_color'] = $colors['green_secondary'];
                $subscription_data['border_color']    = $colors['green_border'];
                $subscription_data['sub_color']       = $colors['green_sub'];
            }
        } else if ( $sub_status == 'on-hold' || $sub_status == 'cancelled' || $sub_status == 'expired' ) {
            if ( self::is_service_inactive( $subscription ) ) {
                $subscription_data['primary_color']   = $colors['green_primary'];
                $subscription_data['secondary_color'] = $colors['green_secondary'];
                $subscription_data['border_color']    = $colors['green_border'];
                $subscription_data['sub_color']       = $colors['green_sub'];
            }
        }
        
        // Offsetter
        // $subscription_data = apply_filters( 'CF_status_extender-get_subscription_data', $subscription_data, $subscription );
        // if ( CF_Renew_Offsetter::$DEFAULTS['status'] == 'enabled' && CF_Renew_Offsetter::offsetter_enabled( $subscription ) ) {
        //     $subscription_data = CF_Renew_Offsetter::get_subscription_data( $subscription_data, $subscription );
        // }

        if ( self::has_unresolved_orders( $subscription ) ) {
            $subscription_data['secondary_color'] = $colors['brown_secondary'];
        }
        
        return $subscription_data;
    }
    
    public static function get_order_type( $order ) {
        if ( wcs_order_contains_early_renewal( $order ) ) {
            $orderTypeText = 'Early Renewal';
        } else if ( wcs_order_contains_renewal( $order ) ) {
            $orderTypeText = 'Renewal';
        } else if ( wcs_order_contains_resubscribe( $order ) ) {
            $orderTypeText = 'Resubscribe';
        } else if ( wcs_order_contains_switch( $order ) ) {
            $orderTypeText = 'Switch';
        } else if ( wcs_order_contains_subscription( $order, 'parent' ) ) {
            $orderTypeText = 'Parent';
        } else if ( wcs_is_subscription( $order ) ) { 
            $orderTypeText = 'Subscription';
        } else {
            $orderTypeText = 'Error...';
        }

        return $orderTypeText;
    }

    public static function get_subscription_meta_options( $order ) {
        if ( CF_Status_Extender::is_service_active( $order ) ) {
            $order_status_label = 'ON';
            $order_status_value = 'on';
        } else if ( CF_Status_Extender::is_service_inactive( $order ) ) {
            $order_status_label = 'OFF';
            $order_status_value = 'off';
        } else {
            $order_status_label = '*OFF';
            $order_status_value = '';
        }

        $input_options = array(
            'id'      => 'cf-subscription-status',
            'label'   => 'Service Status',
            'value'   => $order_status_value,
            'options' => array(
                'off' => 'OFF',
                'on' => 'ON'
            ),
            // 'style' => 'width:16px', // required for checkboxes and radio buttons
            'wrapper_class' => 'form-field' // always add this class
        );

        return array( $input_options, $order_status_label ) ;
    }

    public static function get_order_meta_options( $order ) {
        if ( CF_Status_Extender::is_order_processed( $order ) ) {
            $order_status_label = 'Processed';
            $order_status_value = 'processed';
        } else if ( CF_Status_Extender::is_order_not_processed( $order ) ) {
            $order_status_label = 'Not Processed';
            $order_status_value = 'not_processed';
        } else {
            $order_status_label = '*Not Processed';
            $order_status_value = '';
        }

        $input_options = array(
            'id'      => 'cf-order-status',
            'label'   => 'Order Status',
            'value'   => $order_status_value,
            'options' => array(
                'not_processed' => 'Not Processed',
                'processed'     => 'Processed'
            ),
            // 'style' => 'width:16px', // required for checkboxes and radio buttons
            'wrapper_class' => 'form-field', // always add this class
            // 'custom_attributes' => array(
            //     'disabled' => 'disabled'
            // )
        );

        return array( $input_options, $order_status_label ) ;
    }


    public static function get_item_primary_color( $item ) {
        if ( wcs_is_subscription( $item ) ) {
            $data = self::get_subscription_data( $item );
        } else {
            $data = self::get_order_data( $item );
        }

        return $data['primary_color'];
    }


    public static function is_order_processed( $order ) {
        $meta_value = $order->get_meta( 'CF_order_status' );

        if ( $meta_value === 'processed' ) {
            return true;
        }

        return false;
    }

    public static function is_order_not_processed( $order ) {
        $meta_value = $order->get_meta( 'CF_order_status' );

        if ( $meta_value === 'not_processed' ) {
            return true;
        }

        return false;
    }

    public static function is_service_active( $subscription ) {
        $meta_value = $subscription->get_meta( 'CF_service_status' );

        if ( $meta_value === 'on' ) {
            return true;
        }

        return false;
    }

    public static function is_service_inactive( $subscription ) {
        $meta_value = $subscription->get_meta( 'CF_service_status' );

        if ( $meta_value === 'off' ) {
            return true;
        }

        return false;
    }

    public static function is_service_unset( $subscription ) {
        if ( ! $subscription->meta_exists( 'CF_service_status' ) ) {
            return true;
        }

        $meta_value = $subscription->get_meta( 'CF_service_status' );
        
        if ( $meta_value === '' ) {
            return true;
        } 
        
        return false;
    }

    // public static function is_service_value_valid( $subscription ) {
    //     if ( self::is_service_unset( $subscription ) ) {
    //         return false;
    //     }

    //     $meta_value = $subscription->get_meta( 'CF_service_status' );

    //     if ( $meta_value === 'off' || $meta_value === 'on' ) {
    //         return true;
    //     }

    //     return false;
    // }

    public static function get_table_CSS( $item_data ) {
        $colors = self::$colors;

		return <<<EOT
            <style>
                #order-{$item_data['id']} .check-column {
                    background-color: {$item_data['primary_color']} !important;
                    border-right: {$item_data['border_color']} 1px solid !important;
                    box-shadow: 0px 1px 0px 0px grey inset !important;
                }
                #order-{$item_data['id']} {
                    background-color: {$item_data['secondary_color']} !important;
                    box-shadow: 0px 1px 0px 0px grey inset !important;
                }
            </style>
        EOT;    
	}

    public static function get_sub_pseudo_button_CSS() {
        $colors = self::$colors;

		return <<<EOT
			<style>
				.subscription-status.status-active {
					border: {$colors['sub-active']} 1px solid !important;
				}
				.subscription-status.status-on-hold {
					border: {$colors['sub-on-hold']} 1px solid !important;
				}
				.subscription-status.status-pending-cancel {
					border: {$colors['sub-pending-cancellation']} 1px solid !important;
				}
				.subscription-status.status-cancelled {
					border: {$colors['sub-cancelled']} 1px solid !important;
				}
				.subscription-status.status-expired {
					border: {$colors['sub-expired']} 1px solid; !important
				}
			</style>
		EOT;
	}

    public static function get_order_pseudo_button_CSS() {
        $colors = self::$colors;

		return <<<EOT
            <style>
                .order-status.status-completed {
                    border: {$colors['order-completed']} 1px solid !important;
                }
                .order-status.status-processing {
                    border: {$colors['order-processing']} 1px solid !important;
                }
                .order-status.status-pending {
                    border: {$colors['order-pending-payment']} 1px solid; !important
                }
                .order-status.status-on-hold {
                    border: {$colors['order-on-hold']} 1px solid; !important
                }
                .order-status.status-failed {
                    border: {$colors['order-failed']} 1px solid !important;
                }
                .order-status.status-cancelled {
                    border: {$colors['order-cancelled']} 1px solid !important;
                }
                .order-status.status-refunded {
                    border: {$colors['order-refunded']} 1px solid; !important
                }
                .order-status.status-draft {
                    border: {$colors['order-draft']} 1px solid; !important
                }
            </style>
        EOT;
	}
}   
