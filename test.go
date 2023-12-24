package intellij_platform_plugin_template_main

import (
	"fmt"
	"strconv"
)

func t() {
	atoi, err := strconv.Atoi("1")
	if err != nil {
		return
	}

	for {
		if true {
			break
		}
	}
	switch atoi {
	case 1:
		fmt.Print(atoi)
	case 2:
	default:

	}

	fmt.Print(atoi)
}
