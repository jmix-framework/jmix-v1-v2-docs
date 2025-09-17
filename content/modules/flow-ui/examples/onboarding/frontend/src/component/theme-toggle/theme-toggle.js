// tag::web-component[]
import {html, LitElement} from 'lit';
import {PolylitMixin} from '@vaadin/component-base/src/polylit-mixin.js';
import {defineCustomElement} from '@vaadin/component-base/src/define.js';
import {ElementMixin} from '@vaadin/component-base/src/element-mixin.js';
import {TooltipController} from "@vaadin/component-base/src/tooltip-controller";
import {css, ThemableMixin} from '@vaadin/vaadin-themable-mixin/vaadin-themable-mixin.js';
import {buttonStyles} from '@vaadin/button/src/vaadin-button-core-styles.js';
import {button as buttonLumoStyles} from '@vaadin/button/theme/lumo/vaadin-button-styles.js';
import {ButtonMixin} from '@vaadin/button/src/vaadin-button-mixin.js';

const themeToggleStyles = css`
    :host {
        background: transparent;
        color: var(--lumo-text-color);
        min-width: var(--lumo-button-size);
        padding-left: calc(var(--lumo-button-size) / 4);
        padding-right: calc(var(--lumo-button-size) / 4);
    }
`;

class ThemeToggle extends ButtonMixin(ElementMixin(ThemableMixin(PolylitMixin(LitElement)))) { // <1>

    static get is() {
        return 'theme-toggle'; // <2>
    }

    static get styles() { // <3>
        return [buttonStyles, buttonLumoStyles, themeToggleStyles];
    }

    render() { // <4>
        return html`
            <div class="vaadin-button-container">
                <vaadin-icon icon="vaadin:adjust"></vaadin-icon>
            </div>

            <slot name="tooltip"></slot>
        `;
    }

    static get properties() { // <5>
        return {
            ariaLabel: {
                type: String,
                value: 'Theme toggle',
                reflectToAttribute: true,
            }
        };
    }

    constructor() {
        super();

        this._storageKey = "app-theme";
        this.addEventListener('click', () => this.toggleTheme());
    }

    /** @protected */
    ready() {
        super.ready();

        this._tooltipController = new TooltipController(this); // <6>
        this.addController(this._tooltipController);
        this.applyStorageTheme();
    }

    applyStorageTheme() {
        let storageTheme = this.getStorageTheme();
        let currentTheme = this.getCurrentTheme();
        if (storageTheme && currentTheme !== storageTheme) {
            this.applyTheme(storageTheme);
        }
    }

    getStorageTheme() {
        return localStorage.getItem(this._storageKey);
    }

    getCurrentTheme() {
        return document.documentElement.getAttribute("theme");
    }

    toggleTheme() {
        const theme = this.getCurrentTheme();
        this.applyTheme(theme === "dark" ? "" : "dark");
    }

    applyTheme(theme) {
        document.documentElement.setAttribute("theme", theme);
        localStorage.setItem(this._storageKey, theme);

        const customEvent = new CustomEvent('theme-changed', {detail: {value: theme}});
        this.dispatchEvent(customEvent); // <7>
    }
}

defineCustomElement(ThemeToggle); // <8>

export {ThemeToggle};
// end::web-component[]