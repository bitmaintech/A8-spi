
DESCRIPTION = "Kernel Module Bitmain spi Fpga Driver"
HOMEPAGE = "http://www.example.com"
SECTION = "kernel/modules"
PRIORITY = "optional"
LICENSE = "CLOSED"
KERNEL_VERSION="3.8.13"

RRECOMMENDS_${PN} = "kernel (= ${KERNEL_VERSION})"
DEPENDS = "virtual/kernel"
PR = "r0"

SRC_URI = " \
	file://bitmain-asic-drv.c \
	file://bitmain-asic.h \
file://fpga.c \
file://fpga.h \
file://sha2.c \
file://sha2.h \
file://spi.c \
file://spi.h \
	file://Makefile \
	"

S = "${WORKDIR}"

inherit module

do_compile () {
	echo "compile bitmain-spi"
	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS CC LD CPP
	if [ x"C1" == x"${Miner_TYPE}" ]; then
		export BOARD_TYPE="-DC1_02"
	elif [ x"C2" == x"${Miner_TYPE}" ]; then
		export BOARD_TYPE="-DC1_02"
	elif [ x"S5" == x"${Miner_TYPE}" ]; then
		export BOARD_TYPE="-DS5"
	elif [ x"S2" == x"${Miner_TYPE}" ]; then
		export BOARD_TYPE="-DS2"
	elif [ x"S4+" == x"${Miner_TYPE}" ]; then
		export BOARD_TYPE="-DS4_PLUS"
	fi
	oe_runmake 'MODPATH="${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers"' \
		'KERNEL_SOURCE="${STAGING_KERNEL_DIR}"' \
		'KDIR="${STAGING_KERNEL_DIR}"' \
		'KERNEL_VERSION="${KERNEL_VERSION}"' \
		'CC="${KERNEL_CC}"' \
		'LD="${KERNEL_LD}"'

}


do_install () {
	install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/bitmain
	install -m 0644 ${S}/bitmain_spi*${KERNEL_OBJECT_SUFFIX} ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/bitmain
}
