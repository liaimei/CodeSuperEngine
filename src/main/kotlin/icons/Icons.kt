package icons

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

/**
 * 图标对象，包含各种图标的定义。
 */
object Icons {
    /**
     * 语言图标，用于表示不同语言或语言相关组件的图标。
     */
    object Language {
        /**
         * Jimmer语言图标。
         */
        val Jimmer: Icon = IconLoader.getIcon("icons/jimmer/jimmer.svg", javaClass)

        /**
         * Mybatis语言图标。
         */
        val Mybatis: Icon = IconLoader.getIcon("icons/mybatis/mapper_statement.svg", javaClass)
    }

    /**
     * 图片图标，用于表示各种图片或图片相关组件的图标。
     */
    object Images {
        /**
         * Jimmer图片图标。
         */
        val Jimmer_Dto: Icon = IconLoader.getIcon("icons/jimmer/jimmer.svg", javaClass)

        /**
         * Mapper语句图片图标。
         */
        val Mapper_Statement: Icon = IconLoader.getIcon("icons/mybatis/mapper_statement.svg", javaClass)

        /**
         * Mapper方法图片图标。
         */
        val Mapper_Method: Icon = IconLoader.getIcon("icons/mybatis/mapper_method.svg", javaClass)

        /**
         * 原始Mapper类图片图标。
         */
        val Origin_Mapper_Class: Icon = IconLoader.getIcon("icons/mybatis/origin_mapper_class.svg", javaClass)

        /**
         * 原始Mapper XML图片图标。
         */
        val Origin_Mapper_Xml: Icon = IconLoader.getIcon("icons/mybatis/origin_mapper_xml.svg", javaClass)
    }
}
