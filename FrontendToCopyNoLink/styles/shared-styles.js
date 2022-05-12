import '@polymer/polymer/lib/elements/custom-style.js';
const $_documentContainer = document.createElement('template');

$_documentContainer.innerHTML = `<dom-module id="bakery-dialog-overlay-theme" theme-for="vaadin-dialog-overlay"> 
  <template> 
   <style>
      :host {
        padding: var(--lumo-space-xs);
      }

      [part="overlay"] {
        display: flex;
        animation: none !important;
        min-width: 300px;
        outline: none;
      }

      [part="content"] {
        width: 100%;
        box-sizing: border-box;
        flex: 1 1 auto;
        display: flex;
        flex-direction: column;
        padding: 10px !important;
      }

      :host([theme="left"]) {
        align-items: flex-start;
      }

      :host([theme\$="right"]) {
        align-items: flex-end;
      }

      :host([theme]) [part="overlay"] {
        max-width: 65em;
        width: 100%;
      }

      :host([theme="left"]) [part="overlay"],
      :host([theme="right"]) [part="overlay"] {
        flex: 1;
        max-width: 45em;
      }

      :host([theme="top-right"]) [part="overlay"] {
        position: absolute;
        top: var(--lumo-space-xs);
      }

      @media (max-width: 600px), (max-height: 600px) {
        :host([theme]) {
          top: 0;
          left: 0;
          right: 0;
          bottom: 0;
          padding: 0;
        }

        :host([theme]) [part="overlay"] {
          flex: 1;
          width: 100%;
          border-radius: 0 !important;
        }
      }

      /* we need explicitly set height for wrappers inside dialog-flow */
      [part="content"] ::slotted(flow-component-renderer) {
        height: 100%;
      }
    </style> 
  </template> 
 </dom-module><dom-module id="bakery-text-field-theme" theme-for="vaadin-text-field"> 
  <template> 
   <style>
      :host([status="new"]) [part~="input-field"],
      :host([status="confirmed"]) [part~="input-field"] {
        color: var(--lumo-primary-color);
        background: var(--lumo-primary-color-10pct);
      }

      :host([status="ready"]) [part~="input-field"],
      :host([status="delivered"]) [part~="input-field"] {
        color: var(--lumo-success-color);
        background: var(--lumo-success-color-10pct);
      }

      :host([status="problem"]) [part~="input-field"],
      :host([status="cancelled"]) [part~="input-field"] {
        color: var(--lumo-error-color);
        background: var(--lumo-error-color-10pct);
      }

      :host([theme="white"]) [part~="input-field"] {
        color: var(--lumo-secondary-text-color);
        background-color: var(--lumo-base-color);
      }
    </style> 
  </template> 
 </dom-module>
 
 
 
 
 
 <dom-module id="shared-styles"> 
  <template> 
   <style>
      *,
      *::before,
      *::after,
      ::slotted(*) {
        box-sizing: border-box;
      }

      :host([hidden]),
      [hidden] {
        display: none !important;
      }

      h2,
      h3 {
        margin-top: var(--lumo-space-m);
        margin-bottom: var(--lumo-space-s);
      }

      h2 {
        font-size: var(--lumo-font-size-xxl);
      }

      h3 {
        font-size: var(--lumo-font-size-xl);
      }

      .scrollable {
        padding: var(--lumo-space-m);
        overflow: auto;
        -webkit-overflow-scrolling: touch;
      }

      .count {
        display: inline-block;
        background: var(--lumo-shade-10pct);
        border-radius: var(--lumo-border-radius);
        font-size: var(--lumo-font-size-s);
        padding: 0 var(--lumo-space-s);
        text-align: center;
      }

      .total {
        padding: 0 var(--lumo-space-s);
        font-size: var(--lumo-font-size-l);
        font-weight: bold;
        white-space: nowrap;
      }

      @media (min-width: 600px) {
        .total {
          min-width: 0;
          order: 0;
          padding: 0 var(--lumo-space-l);
        }
      }

      @media (max-width: 600px) {
        search-bar {
          order: 1;
        }
      }

      .flex {
        display: flex;
      }

      .flex1 {
        flex: 1 1 auto;
      }

      .bold {
        font-weight: 600;
      }

      flow-component-renderer[theme="dialog"],
      flow-component-renderer[theme="dialog"] > div {
        display: flex;
        flex-direction: column;
        flex: auto;
      }
    </style> 
  </template> 
 </dom-module>
 
// <dom-module id="bakery-grid-theme" theme-for="vaadin-grid"> 
//  <template> 
//   <style>
//      :host {
//        width: 100%;
//        margin: auto;
//      }
//
//      [part~="row"]:last-child [part~="header-cell"],
//      [part~="header-cell"]:not(:empty):not([details-cell]) {
//        padding-top: var(--lumo-space-l);
//        padding-bottom: var(--lumo-space-m);
//
//        font-size: var(--lumo-font-size-s);
//        border-bottom: 1px solid var(--lumo-shade-5pct);
//      }
//
//      :host(:not([theme~="no-row-borders"])) [part~="cell"]:not([part~="details-cell"]) {
//        border-top: 1px solid var(--lumo-shade-5pct);
//      }
//
//      /* a special grid theme for the bakery storefront view */
//      :host([theme~="orders"]) {
//        background: transparent;
//      }
//
//      :host([theme~="orders"]) [part~="cell"]:not(:empty):not([details-cell]) {
//        padding: 0;
//      }
//
//      :host([theme~="orders"]) [part~="row"][selected] [part~="cell"] {
//        background: transparent !important;
//      }
//
//      :host([theme~="orders"]) [part~="body-cell"] {
//        background: transparent;
//      }
//
//      @media (max-width: 600px) {
//        :host([theme~="orders"]) [part~="cell"] ::slotted(vaadin-grid-cell-content) {
//          padding: 0 !important;
//        }
//      }
//
//      :host([theme~="dashboard"]) [part~="cell"] ::slotted(vaadin-grid-cell-content) {
//        padding: 0;
//      }
//
//      :host([theme~="crud"]) {
//        max-width: calc(964px + var(--lumo-space-m));
//        background-color: var(--lumo-base-color);
//      }
//    </style> 
//  </template> 
// </dom-module>

 <dom-module id="dynamic-grid-tys" theme-for="vaadin-grid-pro"> 
  <template> 
   <style>
      :host {
        font-size: 12px;
        color: #000040;
      }
	[part~="header-cell"] ::slotted(vaadin-grid-cell-content), [part~="footer-cell"] ::slotted(vaadin-grid-cell-content), [part~="reorder-ghost"] {
	    font-size: 12px;
	    font-weight: 800;
//	    background: #a4a5dd !important;
	    background: #4c6ba3 !important;
	    color: white;
	    padding: 0 3px;
	    margin: 0;
	}
	[part~="row"] {
	    height: 30px;
	    padding:0;
	    margin:0;
	}
	 [part="row"]:only-child [part~="header-cell"] {
	    min-height: 30px;
	}
	[part~="cell"] {
//	    background-color: #dddddd;
	    background-color: #eaeaea;
	    border-right: 1px solid #c9cdd1;
	}
		[part~="header-cell"] {	
//		  background: #a4a5dd !important;
		  background: #4c6ba3 !important;
	}
	:host(:not([theme~="no-row-borders"])) [part="row"]:last-child [part~="header-cell"] {
    	border: #c9cdd1 1px solid;
	}
	:host([height-by-rows]) #items,
    :host([height-by-rows]) #outerscroller {
        overflow: hidden;
    }
    </style> 
  </template> 
 </dom-module>
 <dom-module id="vaadin-text-field-tys" theme-for="vaadin-text-field"> 
  <template> 
   <style>
      :host {
        font-size: 11px;
        color: #000040;
        padding: 0 !important;
      }
      [part="label"] {
        color: #000040 !important;
        font-size:11px !important;
        height: 16px;
      }
      [part="input-field"] {
	    border-radius: 0 !important;
		background-color: white !important;
		padding: 0 !important;
		font-weight: normal !important;
		line-height: 0 !important;
		font-size: 13px;
		height: 23px;
		border: 1px solid #c9cdd1;
	}
	:host([has-label])::before {
	    margin-top: 3px !important;
	    margin-bottom: 7px !important;
	}
	:host {
			position: relative;
			top: 4px;
	} 
	:host([has-label]) {
	    padding: 0 !important;
		top: 0;
	}
	:host([readonly]) [part="input-field"] {
	    color: var(--lumo-secondary-text-color);
	    cursor: default;
	    background: #ffffe4 !important;
	}
	:host([readonly]) [part="input-field"]::after {
	    border: none !important;
	}
    </style> 
  </template> 
 </dom-module>
 <dom-module id="vaadin-form-item-tys" theme-for="vaadin-form-item"> 
  <template> 
   <style>
    [part="label"] {
        color: #000040 !important;
        font-size:11px !important;
        margin: 0 !important;
    }
    </style> 
  </template> 
 </dom-module>
 <dom-module id="vaadin-text-area-tys" theme-for="vaadin-text-area"> 
  <template> 
   <style>
      :host {
        font-size: 11px;
        color: #000040;
        padding: 0 !important;
        margin: 0 !important;
      }
      [part="label"] {
        color: #000040 !important;
        font-size:11px !important;
      }
      [part="input-field"] {
	    border-radius: 0 !important;
		background-color: white !important;
		padding: 0 !important;
		font-weight: normal !important;
		font-size: 13px;
		border: 1px solid #c9cdd1;
	}
	:host([has-label])::before {
	    margin-top: 3px !important;
	    margin-bottom: 7px !important;
	}
/*	:host {
		position: relative;
		top: 13px;
	} */
	:host([has-label]) {
	    padding: 0 !important;
	    top: 0;
	}
	:host([readonly]) [part="input-field"] {
	    color: var(--lumo-secondary-text-color);
	    cursor: default;
	    background: #ffffe4 !important;
	}
	:host([readonly]) [part="input-field"]::after {
	    border: none !important;
	}
    </style> 
  </template> 
 </dom-module>
 
  <dom-module id="vaadin-button-tys" theme-for="vaadin-button"> 
  <template> 
   <style>
      :host {
		--lumo-button-size: var(--lumo-size-s) !important;
        font-size: 13px !important;
        color: #982e5e !important;
//        padding: 0 !important;
        margin-right: 4px !important;
//        border-radius: 0 !important;
      }
	:host([theme~="primary"]) {
//    	background-color: #c6c6c6 !important;
    	background-color: #acbcd9 !important;
    }
    :host([theme~="titulomenu"]:hover)::before {
		opacity: 0;
    }
    :host([theme~="entradamenu"]:hover)::before {
		opacity: 0;
    }
    :host([theme~="entradamenu"]:hover) {
		cursor: pointer;
	}
//    :host([theme~="titulomenu"]) {
//        width: 100%;
//		background: transparent;
//		color: black !important;
//    }
    :host([theme~="titulomenu"]) {
        width: 100%;
		background: #4c6ba3;
		color: white !important;
		border-bottom: none !important;
		border-radius: 0;
    }
    :host([theme~="titulomenu"]) .vaadin-button-container {
        justify-content: left !important;
    	font-size: 20px;
    }
//    :host([theme~="entradamenu"]) {
//		background: transparent;
//		height: 10px;
//		line-height: 10px;
//	}
    :host([theme~="entradamenu"]) {
		background: transparent;
		height: 4px;
		line-height: 4px;
		margin-left: 10px;
		border-left: 10px solid #4c6ba3 !important;
	}
    :host([theme~="t-green"]) {
		border-bottom: solid 7px darkgreen;
    }
    :host([theme~="e-green"]) {
		border-left: 10px solid darkgreen;
	}
    :host([theme~="t-aqua"]) {
		border-bottom: solid 7px aqua;
    }
    :host([theme~="e-aqua"]) {
		border-left: 10px solid aqua;
	}
    :host([theme~="t-yellow"]) {
		border-bottom: solid 7px #f3f41d;
    }
    :host([theme~="e-yellow"]) {
		border-left: 10px solid #f3f41d;
	}
    :host([theme~="t-violet"]) {
		border-bottom: solid 7px blueviolet;
    }
    :host([theme~="e-violet"]) {
		border-left: 10px solid blueviolet;
	}
    :host([theme~="t-brown"]) {
		border-bottom: solid 7px brown;
    }
    :host([theme~="e-brown"]) {
		border-left: 10px solid brown;
	}
    :host([theme~="t-orange"]) {
		border-bottom: solid 7px darkorange;
    }
    :host([theme~="e-orange"]) {
		border-left: 10px solid darkorange;
	}
    :host([theme~="t-greenyellow"]) {
		border-bottom: solid 7px greenyellow;
    }
    :host([theme~="e-greenyellow"]) {
		border-left: 10px solid greenyellow;
	}
    :host([theme~="nuevacolumna"]) {
		margin-bottom: 50%;
	}
    :host([theme~="marginbottom30"]) {
		margin-bottom: 30px;
	}
    </style> 
  </template> 
 </dom-module>
 
 <dom-module id="vaadin-checkbox-tys" theme-for="vaadin-checkbox"> 
  <template> 
   <style>
      :host {
		font-size: 12px !important;
    	position: relative;
      }

    </style> 
  </template> 
 </dom-module>

 <dom-module id="vaadin-tab-tys" theme-for="vaadin-tab"> 
  <template> 
   <style>
      :host {
		font-size: 13px !important;
      	color: #982e5e !important;
      }
      :host([selected])::before, :host([selected])::after {
		background-color: #464646 !important;
	  }
      :host([selected]){
		font-size: 14px !important;
//      	color: #515151 !important;
//      	background: #f7f7f7;
      	color: #4c6ba3 !important;
      	background: #e4eaf1;
      }
    </style> 
  </template> 
 </dom-module>

 <dom-module id="vaadin-tabs-tys" theme-for="vaadin-tabs"> 
  <template> 
   <style>
      :host {
		margin-bottom: 10px;
      }
    </style> 
  </template> 
 </dom-module>

 <dom-module id="vaadin-form-layout-tys" theme-for="vaadin-form-layout"> 
  <template> 
   <style>
      :host {
    	padding-top: 10px !important;
	  }
    </style> 
  </template> 
 </dom-module>	 
<dom-module id="grid-height-by-rows-scrollbar-fix" theme-for="vaadin-grid">
  <template>
    <style>
      :host([height-by-rows]) #items,
      :host([height-by-rows]) #outerscroller {
        overflow: hidden;
      }
    </style>
  </template>
</dom-module>
<dom-module id="vaadin-login-overlay-wrapper-tys" theme-for="vaadin-login-overlay-wrapper"> 
  <template> 
   <style>
	[part="brand"] {
	    background-color: white !important;
	    border-bottom: #284c96 10px solid;
	    color: transparent !important;
	    background-image: url("images/logos/tys3.png");
	}
	[part="description"] {
		color: #284c96 !important;
		text-align: center;
	}
    </style>
  </template>
</dom-module>
<dom-module id="vaadin-login-form-wrapper-tys" theme-for="vaadin-login-form-wrapper"> 
  <template> 
   <style>
   :host {
		background: #f0f0ee !important;
	}
    </style>
  </template>
</dom-module>
<dom-module id="dynamic-qry-grid-display-tys" theme-for="dynamic-qry-grid-display"> 
  <template> 
   <style>
    #divQuery {
    	background: #e0e4eb !important;
    }
    </style>
  </template>
</dom-module>
<dom-module id="dynamic-qry-grid-tys" theme-for="dynamic-qry-grid"> 
  <template> 
   <style>
    #divQuery {
    	background: #e0e4eb !important;
    }
    </style>
  </template>
</dom-module>
 <custom-style> 
  <style>
    @keyframes v-progress-start {
      0% {
        width: 0%;
      }
      100% {
        width: 50%;
      }
    }

    .v-loading-indicator,
    .v-system-error,
    .v-reconnect-dialog {
    	position: absolute;
    	left: 0;
    	top: 0;
    	border: none;
    	z-index: 10000;
    	pointer-events: none;
    }

    .v-system-error,
    .v-reconnect-dialog {
    	display: flex;
    	right: 0;
    	bottom: 0;
    	background: var(--lumo-shade-40pct);
    	flex-direction: column;
      align-items: center;
      justify-content: center;
      align-content: center;
    }

    .v-system-error .caption,
    .v-system-error .message,
    .v-reconnect-dialog .text {
    	width: 30em;
    	max-width: 100%;
    	padding: var(--lumo-space-xl);
    	background: var(--lumo-base-color);
    	border-radius: 4px;
    	text-align: center;
    }

    .v-system-error .caption {
    	padding-bottom: var(--lumo-space-s);
    	border-bottom-left-radius: 0;
    	border-bottom-right-radius: 0;
    }

    .v-system-error .message {
    	pointer-events: all;
    	padding-top: var(--lumo-space-s);
    	border-top-left-radius: 0;
    	border-top-right-radius: 0;
    	color: grey;
    }

    .v-loading-indicator {
    	position: fixed !important;
    	width: 50%;
    	opacity: 0.6;
    	height: 4px;
    	background: var(--lumo-primary-color);
    	transition: none;
    	animation: v-progress-start 1000ms 200ms both;
    }

    .v-loading-indicator[style*="none"] {
    	display: block !important;
    	width: 100% !important;
    	opacity: 0;
    	transition: opacity 500ms 300ms, width 300ms;
    	animation: none;
    }
    
    
    body {
	    color: #29477d !important;
//	    background: #f0f0f0 !important;
	    background: #f9f9f9 !important;
	}
  </style> 
 </custom-style>`;

document.head.appendChild($_documentContainer.content);
