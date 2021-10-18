import '@polymer/polymer/polymer-legacy.js';
import '@vaadin/vaadin-crud/src/vaadin-crud.js';
import '@vaadin/vaadin-grid/src/vaadin-grid.js';
import '@vaadin/vaadin-grid/src/vaadin-grid-column.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
import { PolymerElement } from '@polymer/polymer/polymer-element.js';
class CrudView extends PolymerElement {
  static get template() {
    return html`
   <style include="shared-styles">
            :host {
                display: block;
            }
        </style>  
   <!-- <vaadin-crud items="[[users.results]]" include="name.first, name.last, location.street,location.city, location.postcode"></vaadin-crud> --> 
   <iron-ajax auto="" url="http://cloud1.intergal.coop:8083/rest/default/gferPrueba/v1/CR-actividadprofesional" handle-as="json" last-response="{{users}}" headers="{&quot;Authorization&quot;:&quot;CALiveAPICreator Vt3qo5vXaY7W9HgkFenf:1&quot;}"></iron-ajax> 
   <vaadin-crud items="[[users]]" include="SECTOR, ACTIVIDAD, DESCRIPCION"></vaadin-crud> 
   <div id="divCrud" style="width: 100%; height: 100%;"> 
    <!-- <vaadin-crud id="vCrud"></vaadin-crud> --> 
   </div> 
`;
  }

  static get is() {
      return 'crud-view';
  }
  static get properties() {
      return {
          // Declare your properties here.
      };
  }
}
customElements.define(CrudView.is, CrudView);

