Selectize.define('dropdown_direction', function(options) {
    var self = this;

    /**
     * Calculates and applies the appropriate position of the dropdown.
     * 
     * Supports dropdownDirection up, down and auto. In case menu can't be fitted it's
     * height is limited to don't fall out of display.
     */
    this.positionDropdown = (function() {
        return function() {
            var $control = this.$control;
            var $dropdown = this.$dropdown;
            var p = getPositions();

            // direction
            var direction = getDropdownDirection(p);
            if (direction === 'up') {
                $dropdown.addClass('direction-up').removeClass('direction-down');
            } else {
                $dropdown.addClass('direction-down').removeClass('direction-up');
            }
            $control.attr('data-dropdown-direction', direction);

            // position
            var isParentBody = this.settings.dropdownParent === 'body';
            var offset = isParentBody ? $control.offset() : $control.position();
            var fittedHeight;

            switch (direction) {
                case 'up':
                    offset.top -= p.dropdown.height;
                    if (p.dropdown.height > p.control.above) {
                        fittedHeight = p.control.above - 15;
                    }
                    break;

                case 'down':
                    offset.top += p.control.height;
                    if (p.dropdown.height > p.control.below) {
                        fittedHeight = p.control.below - 15;
                    }
                    break;
            }

            if (fittedHeight) {
                this.$dropdown_content.css({ 'max-height' : fittedHeight });
            }

            this.$dropdown.css({
                width : $control.outerWidth(),
                top   : offset.top,
                left  : offset.left
            });             
        };
    })();

    /**
     * Gets direction to display dropdown in. Either up or down.
     */
    function getDropdownDirection(positions) {
        var direction = self.settings.dropdownDirection;

        if (direction === 'auto') {
            // down if dropdown fits
            if (positions.control.below > positions.dropdown.height) {
                direction = 'down';
            }
            // otherwise direction with most space
            else {
                direction = (positions.control.above > positions.control.below) ? 'up' : 'down';
            }
        }

        return direction;
    }

    /**
     * Get position information for the control and dropdown element.
     */
    function getPositions() {
        var $control = self.$control;
        var $window = $(window);

        var control_height = $control.outerHeight(false);
        var control_above = $control.offset().top - $window.scrollTop();
        var control_below = $window.height() - control_above - control_height;

        var dropdown_height = self.$dropdown.outerHeight(false);

        return {
            control : {
                height : control_height,
                above : control_above,
                below : control_below
            },
            dropdown : {
                height : dropdown_height
            }
        };
    } 
});