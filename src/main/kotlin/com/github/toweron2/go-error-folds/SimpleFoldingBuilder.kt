package com.github.toweron2.`go-error-folds`



/*
class SimpleFoldingBuilder : FoldingBuilderEx(), DumbAware {

    override fun buildFoldRegions(
        root: PsiElement,
        document: Document,
        quick: Boolean
    ): Array<FoldingDescriptor> {
        // Initialize the group of folding regions that will expand/collapse together.
        val group = FoldingGroup.newGroup(SimpleAnnotator.SIMPLE_PREFIX_STR)
        // Initialize the list of folding regions
        val descriptors: MutableList<FoldingDescriptor> = ArrayList()

        root.accept(object : JavaRecursiveElementWalkingVisitor() {
            override fun visitLiteralExpression(literalExpression: PsiLiteralExpression) {
                super.visitLiteralExpression(literalExpression)

                val value = PsiLiteralUtil.getStringLiteralContent(d)
                if (value != null && value.startsWith(SimpleAnnotator.SIMPLE_PREFIX_STR + SimpleAnnotator.SIMPLE_SEPARATOR_STR)) {
                    val project: Project = literalExpression.project
                    val key = value.substring(
                        SimpleAnnotator.SIMPLE_PREFIX_STR.length + SimpleAnnotator.SIMPLE_SEPARATOR_STR.length
                    )
                    // find SimpleProperty for the given key in the project
                    val simpleProperty =
                        ContainerUtil.getOnlyItem(SimpleUtil.findProperties(project, key))
                    if (simpleProperty != null) {
                        // Add a folding descriptor for the literal expression at this node.
                        descriptors.add(
                            FoldingDescriptor(
                                literalExpression.node,
                                TextRange(
                                    literalExpression.textRange.startOffset + 1,
                                    literalExpression.textRange.endOffset - 1
                                ),
                                group,
                                Collections.singleton(simpleProperty)
                            )
                        )
                    }
                }
            }
        })

        return descriptors.toTypedArray()
    }

    */
/**
     * Gets the Simple Language 'value' string corresponding to the 'key'
     *
     * @param node Node corresponding to PsiLiteralExpression containing a string in the format
     * SIMPLE_PREFIX_STR + SIMPLE_SEPARATOR_STR + Key, where Key is
     * defined by the Simple language file.
     *//*

    @Nullable
    override fun getPlaceholderText(node: ASTNode): String? {
        val psiLiteralExpression = node.psi as? PsiLiteralExpression ?: return null

        val text = PsiLiteralUtil.getStringLiteralContent(psiLiteralExpression)
        if (text == null) {
            return null
        }

        val key = text.substring(SimpleAnnotator.SIMPLE_PREFIX_STR.length +
                SimpleAnnotator.SIMPLE_SEPARATOR_STR.length)

        val simpleProperty = ContainerUtil.getOnlyItem(
            SimpleUtil.findProperties(psiLiteralExpression.project, key)
        )
        if (simpleProperty == null) {
            return StringUtil.THREE_DOTS
        }

        val propertyValue = simpleProperty.value
        // IMPORTANT: keys can come with no values, so a test for null is needed
        // IMPORTANT: Convert embedded \n to backslash n, so that the string will look
        // like it has LF embedded in it and embedded " to escaped "
        return propertyValue?.replace("\n", "\\n")?.replace("\"", "\\\"") ?: StringUtil.THREE_DOTS
    }

    override fun isCollapsedByDefault(node: ASTNode): Boolean {
        return true
    }
}
*/
