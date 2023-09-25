
SUMMARY = "aesdchar driver module"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"


SRC_URI = "git://github.com/cu-ecen-aeld/assignments-3-and-later-Piistachyoo.git;protocol=ssh;branch=master  \
    file://S98aesdchar  \
    "

PV = "1.0+git${SRCPV}"
SRCREV = "a2258fc07f48951582a7cf90c365f033a615d457"

S = "${WORKDIR}/git/aesd-char-driver"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}"
EXTRA_OEMAKE  += "KERNELDIR=${STAGING_KERNEL_DIR}"
RPROVIDES:${PN} += "kernel-module-aesdchar"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "S98aesdchar"
inherit update-rc.d

FILES:${PN} += "${INIT_D_DIR}/${INITSCRIPT_NAME:${PN}}"

FILES:${PN} += "${base_libdir}/modules/${KERNEL_VERSION}/extra/aesdchar_load"
FILES:${PN} += "${base_libdir}/modules/${KERNEL_VERSION}/extra/aesdchar_unload"



do_install:append () {

    install -d ${D}${INIT_D_DIR}
    install -m 0755 ${WORKDIR}/${INITSCRIPT_NAME:${PN}} ${D}${INIT_D_DIR}

	install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra
	install -m 755 ${S}/aesdchar_load ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/aesdchar_load
	install -m 755 ${S}/aesdchar_unload ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/aesdchar_unload
}

